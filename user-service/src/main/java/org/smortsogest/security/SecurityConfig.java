package org.smortsogest.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
            .csrf(AbstractHttpConfigurer::disable)
//            .authorizeHttpRequests(auth -> auth
//                    .requestMatchers("/products/**").permitAll()
//
//                    .requestMatchers("/auth/**").permitAll()
//                    .requestMatchers("/account/**").authenticated()
//                    .requestMatchers("/address/**").authenticated()
//
//                    .requestMatchers("/cart/**").authenticated()
//                    .requestMatchers("/checkout/**").authenticated()
//
//                    .requestMatchers("/admin/products/**").permitAll()
//                    .anyRequest().authenticated()
//            )
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(httpBasic -> {});
        return http.build();
    }
}
