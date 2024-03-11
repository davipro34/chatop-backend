package fr.davipro.chatopbackend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import fr.davipro.chatopbackend.service.CustomUserDetailsService;

@Configuration
public class SpringSecurityConfig {
    
    private final CustomUserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;

    public SpringSecurityConfig(CustomUserDetailsService userDetailsService, BCryptPasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {        
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/auth/login", "/auth/register").permitAll() // Allow access without authentication
                    .anyRequest().authenticated()) // All other requests require authentication
                .httpBasic(Customizer.withDefaults())
                .build();        
    }

    // This method configures Spring Security for authentication based on a DaoAuthenticationProvider with specific services for user details and password comparison.
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
    
        auth.authenticationProvider(authProvider);
    }
}