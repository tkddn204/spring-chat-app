package com.rightpair.config;

import com.rightpair.security.JwtExceptionFilter;
import com.rightpair.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;

@Configuration
@RequiredArgsConstructor
public class SecurityFilterConfig {

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String googleOAuthRedirectUri;
    private final CorsConfigurationSource corsConfigurationSource;
    private final JwtExceptionFilter jwtExceptionFilter;
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain http(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(config -> config.configurationSource(corsConfigurationSource))
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .rememberMe(AbstractHttpConfigurer::disable)
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(request ->
                request.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                        .requestMatchers("/v1/auth/**").permitAll()
                        .requestMatchers("/", "/error", "/csrf").permitAll()
                        .anyRequest().authenticated()
        );

        http.oauth2Login(configurer ->
                configurer.authorizationEndpoint(config -> config.baseUri("/v1/auth/authorize"))
                        .redirectionEndpoint(config -> config.baseUri(googleOAuthRedirectUri))
        );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtExceptionFilter, JwtFilter.class);

        return http.build();
    }
}
