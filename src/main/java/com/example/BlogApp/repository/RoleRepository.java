package com.example.BlogApp.repository;

import com.example.BlogApp.entity.Role;
import com.example.BlogApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(String name);

}
