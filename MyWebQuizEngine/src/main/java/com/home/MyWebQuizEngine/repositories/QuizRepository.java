package com.home.MyWebQuizEngine.repositories;

import com.home.MyWebQuizEngine.domain.Quiz;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends CrudRepository<Quiz, Long>, PagingAndSortingRepository<Quiz, Long> {
    void deleteById(long id);
}
