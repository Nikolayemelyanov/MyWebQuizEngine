package com.home.MyWebQuizEngine.repositories;

import com.home.MyWebQuizEngine.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);

}
