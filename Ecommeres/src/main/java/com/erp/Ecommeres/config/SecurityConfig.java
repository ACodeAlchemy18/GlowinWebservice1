package com.erp.Ecommeres.config;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.erp.Ecommeres.security.CustomUserDetailsService;
import com.erp.Ecommeres.security.TokenAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // ---------- PASSWORD ENCODER ----------
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ---------- AUTH PROVIDER ----------
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // ---------- AUTH MANAGER ----------
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http)
            throws Exception {
        return http.getSharedObject(
                org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder.class)
                .authenticationProvider(authenticationProvider())
                .build();
    }

    // ---------- JWT FILTER ----------
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }

    // ---------- SECURITY FILTER CHAIN ----------
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            // ✅ Enable CORS (CorsConfig handles it)
            .cors(cors -> {})

            // ✅ Disable CSRF for REST APIs
            .csrf(csrf -> csrf.disable())

            // ✅ Stateless session (JWT)
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // ✅ Authorization rules
            .authorizeHttpRequests(auth -> auth

                // ✅ Preflight requests MUST be allowed
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // ✅ Public auth APIs
                .requestMatchers("/auth/**").permitAll()

                // ✅ Public uploads (images)
                .requestMatchers("/uploads/**").permitAll()

                // ✅ Public product viewing
                .requestMatchers(HttpMethod.GET, "/products/**").permitAll()

                // ✅ Admin-only product management
                .requestMatchers(HttpMethod.POST, "/products/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/products/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/products/**").hasRole("ADMIN")

                // ✅ Admin APIs
                .requestMatchers("/api/admin/**").hasRole("ADMIN")

                // ✅ Everything else requires authentication
                .anyRequest().authenticated()
            )

            // ❌ DO NOT block public APIs with aggressive 401
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(
                    (req, res, e) -> {
                        // Let CORS responses pass correctly
                        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    }
                )
            );

        // ✅ JWT filter AFTER CORS
        http.addFilterBefore(
                tokenAuthenticationFilter(),
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }
}