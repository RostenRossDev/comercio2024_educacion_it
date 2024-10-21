package com.example.ecommerce.controller;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.CustomUser;
import com.example.ecommerce.repository.CartIRepository;
import com.example.ecommerce.service.MercadoPagoService;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.service.UserService;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MercadoPagoService mercadoPagoService;


    @Autowired
    private CartIRepository cartIRepository;

    @GetMapping
    public String viewCart(Model model, @SessionAttribute(value = "cart", required = false) Map<Long, Integer> cart) {
        if (cart == null) {
            cart = new HashMap<>();
        }
        Cart findedCart = productService.getCart(userService.getLoggedUsername());
        if(findedCart == null) {
            findedCart = new Cart();
            findedCart.setItems(new ArrayList<>());
        }
        model.addAttribute("cartItems", findedCart);
        model.addAttribute("total", productService.calculateTotal(userService.getLoggedUsername()));
        return "cart";
    }

    @PostMapping("/add/{productId}")
    public String addToCart(@PathVariable Long productId, @RequestParam(defaultValue = "1") int quantity,
                            @SessionAttribute(value = "cart", required = false) Map<Long, Integer> cart) {
        if (cart == null) {
            cart = new HashMap<>();
        }
        cart.merge(productId, quantity, Integer::sum);
        return "redirect:/cart";
    }

    @PostMapping("/remove/{productId}")
    public String removeFromCart(@PathVariable Long productId,
                                 @SessionAttribute(value = "cart", required = false) Map<Long, Integer> cart) {
        if (cart != null) {
            cart.remove(productId);
        }
        return "redirect:/cart";
    }

    @PostMapping("/checkout")
    public String checkout(@AuthenticationPrincipal CustomUser user,
                           @SessionAttribute(value = "cart", required = false) Map<Long, Integer> cart,
                           Model model) {
        if (cart == null || cart.isEmpty()) {
            return "redirect:/cart";
        }

        Order order = orderService.createOrder(user, cart);
        try {
            String preferenceId = mercadoPagoService.createPreference(order);
            model.addAttribute("preferenceId", preferenceId);
            model.addAttribute("orderId", order.getId());
            cart.clear(); // Clear the cart after creating the order
            return "checkout";
        } catch (MPException | MPApiException e) {
            // Handle MercadoPago exception
            return "redirect:/cart?error=payment";
        }
    }

    @GetMapping("/success")
    public String paymentSuccess(@RequestParam("payment_id") String paymentId,
                                 @RequestParam("status") String status,
                                 @RequestParam("external_reference") String orderId) {
        try {
            Payment payment = mercadoPagoService.getPaymentInfo(paymentId);
            Order order = orderService.getOrderById(Long.parseLong(orderId));
            
            if (order != null && "approved".equals(status)) {
                order.setStatus("PAID");
                order.setPaymentId(paymentId);
                orderService.updateOrder(order);
            }
            
            return "redirect:/orders";
        } catch (MPException | MPApiException e) {
            // Handle MercadoPago exception
            return "redirect:/cart?error=payment";
        }
    }

    @GetMapping("/failure")
    public String paymentFailure() {
        return "redirect:/cart?error=payment";
    }

    @GetMapping("/pending")
    public String paymentPending() {
        return "redirect:/orders";
    }
}