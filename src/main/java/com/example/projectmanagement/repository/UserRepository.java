package com.example.projectmanagement.repository;

import com.example.projectmanagement.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User,Long> {
    User findByEmail(String email);
}
//Classes can only extend other classes.
//Interfaces can extend other interfaces.
//Classes implement interfaces to provide method definitions