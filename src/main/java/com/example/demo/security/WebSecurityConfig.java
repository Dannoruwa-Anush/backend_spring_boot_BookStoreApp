package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.security.jwt.AuthEntryPointJwt;
import com.example.demo.security.jwt.AuthTokenFilter;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {
   @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    //Spring Bean annotation is usually declared in Configuration classes methods.
    //Configuring a basic userDetailsService bean
    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }
    //---

    //Configuring a basic authenticationJwtTokenFilter bean
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }
    //---

    //Configuring a basic authenticationProvider bean
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
    //---

    //Configuring a basic passwordEncoder bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //---

    // Configuring authenticationManager bean with constructor injection
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    //---

    // Configuring filterChain bean with constructor injection
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf(csrf -> csrf.disable())
            .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth ->
                // Allow public access to authentication and registration endpoints
                auth.requestMatchers("/auth/***").permitAll()
                
                //----- [Start : Book] ------------------------------------
                // Allow both USER and ADMIN to access /book and /book/{id}
                .requestMatchers("/book/**").hasAnyRole("USER", "ADMIN")

                // Allow only ADMIN to perform save and update operations
                .requestMatchers(HttpMethod.POST, "/book").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/book/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/book/**").hasRole("ADMIN")
                //----- [End : Book] ------------------------------------


                //----- [Start : Category] ------------------------------------
                // Allow both USER and ADMIN to access /category and /category/{id}
                .requestMatchers("/category/**").hasAnyRole("USER", "ADMIN")

                // Allow only ADMIN to perform save and update operations
                .requestMatchers(HttpMethod.POST, "/category").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/category/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/category/**").hasRole("ADMIN")
                //----- [End : Category] ------------------------------------

                .anyRequest().authenticated()
            );

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    //--- 
}
