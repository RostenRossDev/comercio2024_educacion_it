package com.example.ecommerce.service;

import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.OrderItem;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.CustomUser;
import com.example.ecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    public Order createOrder(CustomUser user, Map<Long, Integer> cartItems) {
        Order order = new Order();
        order.setUser(user);
        order.setStatus("PENDING");

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (Map.Entry<Long, Integer> entry : cartItems.entrySet()) {
            Product product = productService.getProductById(entry.getKey());
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(entry.getValue());
            orderItem.setPrice(BigDecimal.valueOf(product.getPrice()));
            order.getItems().add(orderItem);

            totalAmount = totalAmount.add(
                    BigDecimal.valueOf(product.getPrice()).multiply(BigDecimal.valueOf(entry.getValue()))
            );
        }

        order.setTotalAmount(totalAmount);
        return orderRepository.save(order);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public List<Order> getOrdersByUser(CustomUser user) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(user.getId());
    }

    public Order updateOrder(Order order) {
        return orderRepository.save(order);
    }
}