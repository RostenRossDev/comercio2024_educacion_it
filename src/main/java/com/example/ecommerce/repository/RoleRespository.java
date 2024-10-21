package com.example.ecommerce.repository;

import com.example.ecommerce.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRespository extends JpaRepository<Role, Long> {

    Optional<Role> findByAuthority(String name);
}