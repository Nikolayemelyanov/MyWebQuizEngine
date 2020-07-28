package com.home.MyWebQuizEngine.services;

import com.home.MyWebQuizEngine.domain.Quiz;
import com.home.MyWebQuizEngine.domain.QuizResponse;
import com.home.MyWebQuizEngine.domain.SuccessQuiz;
import com.home.MyWebQuizEngine.domain.User;
import com.home.MyWebQuizEngine.repositories.QuizRepository;
import com.home.MyWebQuizEngine.repositories.SuccessQuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class QuizServiceImpl implements QuizService{

    private QuizRepository quizRepository;

    private SuccessQuizRepository successQuizRepository;

    @Autowired
    public void setQuizRepository( QuizRepository quizRepository){
        this.quizRepository = quizRepository;
    }

    @Autowired
    public  void setSuccessQuizRepository(SuccessQuizRepository successQuizRepository) {
        this.successQuizRepository = successQuizRepository;
    }

    @Override
    public Quiz saveQuiz(Quiz quiz, User user) {
        user.getQuizList().add(quiz);
        return quizRepository.save(quiz);
    }

    @Override
    public Quiz updateQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @Override
    public Quiz getQuizById(long id) {
        if (quizRepository.findById(id).isPresent()) {
            return quizRepository.findById(id).get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    @Override
    public List<Quiz> listAllQuizzes() {
        return quizRepository.findAll();
    }

    @Override
    public void deleteQuiz(long id, User user) {
        if (quizRepository.findById(id).isPresent()) {
            if (user.getQuizList().contains(quizRepository.findById(id).get())) {
                user.getQuizList().remove(quizRepository.findById(id).get());
                quizRepository.deleteById(id);
                throw new ResponseStatusException(HttpStatus.NO_CONTENT);
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public QuizResponse solveQuiz(long id, int[] inputAnswers, User user) {
        QuizResponse quizResponse;
        try {
            int[] quizAnswers = quizRepository.findById(id).get().convertToQuizJson().getAnswer();
            if (Arrays.equals(inputAnswers, quizAnswers)) {
                String localDatetime = LocalDateTime.now().toString();
                SuccessQuiz successQuiz = new SuccessQuiz(id, localDatetime);
                user.getSuccessQuizList().add(successQuiz);
                successQuizRepository.save(successQuiz);
                quizResponse = new QuizResponse(true, "Congratulations, you're right");
            } else {
                quizResponse = new QuizResponse(false, "Wrong answer! Please, try again.");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return quizResponse;
    }

    @Override
    public Page<SuccessQuiz> listSolvedQuizzes(User user, Pageable paging) {

        return successQuizRepository.findSuccessQuizByUserid(user.getId(), paging);
    }


}
