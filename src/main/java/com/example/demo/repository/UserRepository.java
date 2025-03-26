package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();

    User findOneById(long id);

    void deleteById(long id);

    User findByEmail(String email);
}
