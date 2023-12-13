package com.rightpair.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    private final List<String> allowMethods = List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS");

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Spring Web Cors 설정
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods(allowMethods.toArray(new String[0]))
                .allowedHeaders("*");
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        // Spring Security Cors 설정
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(List.of("*"));
        config.setAllowedMethods(allowMethods);
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
