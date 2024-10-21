package com.example.ecommerce.controller;

import com.example.ecommerce.model.CustomUser;
import com.example.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String viewOrders(@AuthenticationPrincipal CustomUser user, Model model) {
        model.addAttribute("orders", orderService.getOrdersByUser(user));
        return "orders";
    }
}