package org.engripaye.secureinternaladminportal.config;

import org.engripaye.secureinternaladminportal.service.CustomOidcUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final CustomOidcUserService customOidcUserService;


    public SecurityConfig(CustomOidcUserService customOidcUserService) {
        this.customOidcUserService = customOidcUserService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                // public asset
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/favicon.ico").permitAll()
                // public landing page
                        .requestMatchers("/", "/error", "/access-denied").permitAll()
                //API Endpoint required Auth
                        .requestMatchers("/admin/**").hasRole("ADMIN") // coarse-grain
                // any other result requires authentication
                        .anyRequest().authenticated())
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/oauth2/authorization/google") // or default
                        .userInfoEndpoint(userInfo -> userInfo.oidcUserService(customOidcUserService)
                        )
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"))

                .csrf(csrf -> csrf) // CSRF enabled bt default; ensure form tokens are used in thymeleaf
                .sessionManagement(session -> session
                        .sessionFixation().migrateSession())
                .headers(headers -> headers
                        .contentSecurityPolicy("default-src 'self'") // customize as needed
                        .frameOptions().sameOrigin());

             return http.build();
    }
}
