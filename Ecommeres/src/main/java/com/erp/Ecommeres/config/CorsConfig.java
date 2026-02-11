package com.erp.Ecommeres.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // ✅ EXACT frontend origin (NO wildcard)
        config.setAllowedOrigins(List.of("http://localhost:5174","http://localhost:5173","https://glowinwebapp12.vercel.app","https://glowinwebapp14.vercel.app"));

        // ✅ Allow headers you actually use
        config.setAllowedHeaders(List.of(
                "Authorization",
                "Content-Type",
                "userId"
        ));

        // ✅ Allow methods
        config.setAllowedMethods(List.of(
                "GET", "POST", "PUT", "DELETE", "OPTIONS"
        ));

        // ✅ Required for JWT / Authorization
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
