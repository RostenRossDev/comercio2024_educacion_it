package com.example.ecommerce.repository;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartIRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(CustomUser user);
}