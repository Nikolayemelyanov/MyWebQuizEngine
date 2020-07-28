package com.home.MyWebQuizEngine.controller;
import com.home.MyWebQuizEngine.controller.model.AnswersJson;
import com.home.MyWebQuizEngine.controller.model.QuizJson;
import com.home.MyWebQuizEngine.domain.*;
import com.home.MyWebQuizEngine.services.QuizService;
import com.home.MyWebQuizEngine.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;

@RestController
public class QuizController {

    private QuizService quizService;

    @Autowired
    public void setQuizService(QuizService quizService) {
        this.quizService= quizService;
    }
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){
       this.userService = userService;
    }
    public QuizController() {  }

    User getLoggedUser(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return userService.findUserByUsername(userDetails.getUsername());
    }

    /*
    Get special quiz by Id
     */
    @GetMapping(path = "/api/quizzes/{id}")
    public QuizJson getQuiz(@PathVariable long id) {
        Quiz quiz = quizService.getQuizById(id);
        return quiz.convertToQuizJson();
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
        int startQuiz = (int) paging.getOffset();
        List <Quiz> quizList = quizService.listAllQuizzes();
        int endQuiz = Math.min((startQuiz + paging.getPageSize()), quizList.size());
        List <QuizJson> quizJsonList =  new ArrayList<>();
        for (Quiz val: quizList.subList(startQuiz, endQuiz)
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
        User user = getLoggedUser();
        Quiz inputQuiz = quizJson.convertToQuiz();
        Quiz savedQuiz = quizService.saveQuiz(inputQuiz, user);
        return savedQuiz.convertToQuizJson();
    }
    /*
    Solve a quiz
    */
    @PostMapping(path = "/api/quizzes/{id}/solve", consumes = "application/json")
    public QuizResponse solveQuiz(@PathVariable long id, @RequestBody AnswersJson answersJson) {
        User loggedUser = getLoggedUser();
        return quizService.solveQuiz(id,answersJson.getAnswersArray(),loggedUser);
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
        User loggedUser = getLoggedUser();
        quizService.deleteQuiz(id, loggedUser);
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
        User loggedUser = getLoggedUser();
        return quizService.listSolvedQuizzes(loggedUser, paging);
    }
}
