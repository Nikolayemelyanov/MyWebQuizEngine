package com.home.MyWebQuizEngine.repositories;

import com.home.MyWebQuizEngine.domain.SuccessQuiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuccessQuizRepository extends CrudRepository<SuccessQuiz, Long>,
        PagingAndSortingRepository<SuccessQuiz, Long> {
    @Query(value = "SELECT * FROM SUCCESS_QUIZ where USER_ID = ?1 ORDER BY COMPLETED_AT DESC",
            nativeQuery = true)
    Page<SuccessQuiz> findSuccessQuizByUserid(Long id, Pageable pageable);

}
