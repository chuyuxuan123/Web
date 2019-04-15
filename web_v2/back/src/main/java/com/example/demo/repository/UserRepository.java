package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public interface UserRepository extends CrudRepository<User, Long> {
    @Modifying
    @Query(value = "update user set enable = :enable where username = :username ",nativeQuery = true)
    void updateEnableByUsername(@Param("username") String username,@Param("enable") boolean enable);
}
