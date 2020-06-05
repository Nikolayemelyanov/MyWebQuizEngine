package com.home.MyWebQuizEngine.controller;

import com.home.MyWebQuizEngine.repositories.SuccessQuizRepository;
import com.home.MyWebQuizEngine.security.UserDetailsImpl;
import com.home.MyWebQuizEngine.domain.*;
import com.home.MyWebQuizEngine.repositories.QuizRepository;
import com.home.MyWebQuizEngine.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @GetMapping(path = "/api/quizzes/{id}")
    public Quiz getQuiz(@PathVariable long id) {
        if (quizRepository.findById(id).isPresent()) {
            return quizRepository.findById(id).get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/api/quizzes")
    public Page<Quiz> getQuizPage(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable paging = PageRequest.of(page, pageSize, Sort.by(sortBy));
        return quizRepository.findAll(paging);
    }

    @PostMapping(path = "/api/quizzes", consumes = "application/json")
    public Quiz inputQuiz(@Valid @RequestBody Quiz quiz) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername());
        user.getQuizList().add(quiz);
        return quizRepository.save(quiz);
    }

    @PostMapping(path = "/api/quizzes/{id}/solve", consumes = "application/json")
    public Response solveQuiz(@PathVariable long id, @RequestBody InitialAnswer initialAnswer) {
        Response response;
        try {
            if (Arrays.equals(initialAnswer.getAnswer(), quizRepository.findById(id).get().getAnswer())) {

                UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                User user = userRepository.findByEmail(userDetails.getUsername());
                String localDatetime = LocalDateTime.now().toString();
                SuccessQuiz successQuiz = new SuccessQuiz(id, localDatetime);
                user.getSuccessQuizList().add(successQuiz);
                successQuizRepository.save(successQuiz);
                response = new Response(true, "Nice");
            } else {
                response = new Response(false, "Try again");
            }
            return response;
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

    @DeleteMapping(path = "/api/quizzes/{id}")
    public void deleteQuiz(@PathVariable long id) {
        if (quizRepository.findById(id).isPresent()) {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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

    @GetMapping(path = "/api/quizzes/completed")
    public Page<SuccessQuiz> getQuizCompleted(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable paging = PageRequest.of(page, pageSize, Sort.by(sortBy));
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername());
        return successQuizRepository.findSuccessQuizByUserid(user.getId(), paging);
    }
}
