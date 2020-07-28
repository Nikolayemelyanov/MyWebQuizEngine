package com.home.MyWebQuizEngine.services;

import com.home.MyWebQuizEngine.domain.Quiz;
import com.home.MyWebQuizEngine.domain.QuizResponse;
import com.home.MyWebQuizEngine.domain.SuccessQuiz;
import com.home.MyWebQuizEngine.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuizService {

    Quiz saveQuiz(Quiz quiz, User user);

    Quiz updateQuiz(Quiz quiz);

    Quiz getQuizById(long id);

    List<Quiz> listAllQuizzes();

    void deleteQuiz(long id,User user);

    QuizResponse solveQuiz(long id, int[] answers, User user);

    Page<SuccessQuiz> listSolvedQuizzes(User user, Pageable paging);

}
