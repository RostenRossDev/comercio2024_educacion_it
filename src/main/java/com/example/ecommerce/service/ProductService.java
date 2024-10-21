package com.example.ecommerce.service;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CustomUser;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.CartIRepository;
import com.example.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartIRepository cartIRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Cart getCart(CustomUser user) {
        return cartIRepository.findByUser(user);
    }
    public Double calculateTotal(CustomUser user) {

        Cart cart = cartIRepository.findByUser(user);

        if(cart == null) return 0.0;

        if(cart.getItems().size() == 0) return 0.0;

        return cart.getItems().stream()
                .mapToDouble(product -> product.getProduct().getPrice() * product.getQuantity())
                .sum();
    }

}