package com.codemaster.devflow.repository;

import com.codemaster.devflow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
