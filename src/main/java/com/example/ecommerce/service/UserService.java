package com.example.ecommerce.service;

import com.example.ecommerce.model.CustomUser;
import com.example.ecommerce.model.Role;
import com.example.ecommerce.repository.RoleRespository;
import com.example.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRespository roleRespository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUser u = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<GrantedAuthority> authorities = u.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                .collect(Collectors.toList());
        User user = new User(u.getUsername(),
                u.getPassword(),
                u.getEnabled(),
                true,
                true,
                true,
                authorities);
        return user;
    }

    public CustomUser registerNewUser(CustomUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role role = roleRespository.findByAuthority("ROLE_USER").orElse(null);
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        return userRepository.save(user);
    }

    public List<CustomUser> getAllUsers() {
        return userRepository.findAll();
    }

    public CustomUser getLoggedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String username  =  authentication.getName();  // Devuelve el nombre de usuario
            return userRepository.findByUsername(username).orElse(null);
        }
        return null;  // Si no hay usuario autenticado
    }
}