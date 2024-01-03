package com.example.nutritionapp.repository;

import com.example.nutritionapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findFirstByUsernameAndPassword(String username, String password);
    List<User> findAllByRole(String role);
    List<User> findAllByRoleAndStatus(String role, String status);
}
