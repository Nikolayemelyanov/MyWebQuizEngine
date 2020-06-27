package com.home.MyWebQuizEngine.controller;

import com.home.MyWebQuizEngine.repositories.SuccessQuizRepository;
import com.home.MyWebQuizEngine.security.UserDetailsImpl;
import com.home.MyWebQuizEngine.domain.*;
import com.home.MyWebQuizEngine.repositories.QuizRepository;
import com.home.MyWebQuizEngine.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@RestController
public class QuizManager {
    public QuizManager() {
    }

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private SuccessQuizRepository successQuizRepository;

    /*
    Get special quiz by Id
     */
    @GetMapping(path = "/api/quizzes/{id}")
    public QuizJson getQuiz(@PathVariable long id) {
        if (quizRepository.findById(id).isPresent()) {
            return quizRepository.findById(id).get().convertToQuizJson();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    /*
   Get all quizzes as a Page
    */
    @GetMapping(path = "/api/quizzes")
    public Page<QuizJson> getQuizPage(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable paging = PageRequest.of(page, pageSize, Sort.by(sortBy));
        int start = (int) paging.getOffset();
        List <Quiz> quizList = quizRepository.findAll();
        int end = Math.min((start + paging.getPageSize()), quizList.size());
        List <QuizJson> quizJsonList =  new ArrayList<>();
        for (Quiz val: quizList.subList(start, end)
             ) {
            quizJsonList.add(val.convertToQuizJson());
        }
        return new PageImpl<>(quizJsonList, paging, quizList.size());
    }
    /*
    Save a quiz to the database
    */
    @PostMapping(path = "/api/quizzes", consumes = "application/json")
    public QuizJson inputQuiz(@Valid @RequestBody QuizJson quizJson) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        User user = userRepository.findByEmail(userDetails.getEmail());
        Quiz inputQuiz = quizJson.convertToQuiz();
        user.getQuizList().add(inputQuiz);
        return quizRepository.save(inputQuiz).convertToQuizJson();
    }

    /*
    Solving quiz
    */
    @PostMapping(path = "/api/quizzes/{id}/solve", consumes = "application/json")
    public QuizResponse solveQuiz(@PathVariable long id, @RequestBody AnswerAsJson answerAsJson) {
        QuizResponse quizResponse;
        try {
            if (Arrays.equals(answerAsJson.getAnswersArray(), quizRepository.findById(id).get().convertToQuizJson().getAnswer())) {
                 /*
                Get UserDetails of logged User
                */
                UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();
                User user = userRepository.findByEmail(userDetails.getEmail());
                String localDatetime = LocalDateTime.now().toString();
                SuccessQuiz successQuiz = new SuccessQuiz(id, localDatetime);
                user.getSuccessQuizList().add(successQuiz);
                successQuizRepository.save(successQuiz);
                quizResponse = new QuizResponse(true, "Congratulations, you're right");
            } else {
                quizResponse = new QuizResponse(false, "Wrong answer! Please, try again.");
            }
            return quizResponse;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    /*
       Delete a quiz by logged user
    */
    @DeleteMapping(path = "/api/quizzes/{id}")
    public void deleteQuiz(@PathVariable long id) {
        if (quizRepository.findById(id).isPresent()) {
             /*
             Get UserDetails of logged User
             */
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();
            User user = userRepository.findByEmail(userDetails.getUsername());
            if (user.getQuizList().contains(quizRepository.findById(id).get())) {
                quizRepository.deleteById(id);
                throw new ResponseStatusException(HttpStatus.NO_CONTENT);
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    /*
    Get a Page of solved quizzes of logged user
    */
    @GetMapping(path = "/api/quizzes/completed")
    public Page<SuccessQuiz> getQuizCompleted(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable paging = PageRequest.of(page, pageSize, Sort.by(sortBy));
         /*
         Get UserDetails of logged User
         */
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        User user = userRepository.findByEmail(userDetails.getEmail());
        return successQuizRepository.findSuccessQuizByUserid(user.getId(), paging);
    }
}
