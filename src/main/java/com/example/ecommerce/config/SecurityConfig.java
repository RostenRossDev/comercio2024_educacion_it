package com.example.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/prueba","/", "/home", "/register", "/products", "/h2-console/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated())
            .formLogin(login -> login
                .loginPage("/autenticacion")
                    .defaultSuccessUrl("/", true)
                    //.usernameParameter("email") aca le decimos a spring que use este campo o property para el login en lugar del usarname
                .permitAll())
            .logout(LogoutConfigurer::permitAll)
                .csrf(csrf-> csrf.ignoringRequestMatchers("/h2-console/**"))
                .headers(header -> header.frameOptions(options -> options.disable())); //Por defecto el session cration policy guarda en el estado de conexion en la session HTTP, con STATELESS no se guarda ya que la manejamos en el token
        return http.build();  // Devolvemos un bean de tipo SecurityFilterChain
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}