package com.rajesh.jwt.config;

import com.rajesh.jwt.filter.JwtFilter;
import com.rajesh.jwt.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurity {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    /*
        2. We find the endpoint and verify that does it secure or not. If find secure then move to the filter-Cain
        Here we are using a method as .addFilterBefore() were we are passing our own "jwtFilter" object that will call
        before of UsernamePasswordAuthenticationFilter.class.

        3. Then we call the jwtFilter class for next operation, open it.

        11. Once the SecurityContextHolder has all the detail it authenticates the url and if url has role
        access then it get the authority by SecurityContextHolder and check, does this user has role permission for
        this endpoint if yes then request move to the controller for operation.
     */
    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/journal/**", "/user/**").authenticated() // Require authentication for these paths
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Only ADMIN role can access these paths
                        .anyRequest().permitAll() // Allow all other requests
                )
                .csrf(csrf -> csrf.disable()) // Disable CSRF
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless sessions
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // Add custom JWT filter

        return http.build();
    }

    /*
        Here we are creating the bean of AuthenticationManager because we are use with Autowire in
        Spring Controller
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /*
        After calling the AuthenticationManager, this call the AuthenticationProvider for handling the
        authentication on the behalf of available authentication handling mechanise such as here on the
        behalf of username and password.

        Note: Here we are passing our own custom UserDetailsService for connecting taking the username and password
        details after checking from the Database because the password which are coming from the database is in
        encrypted format that need to match by the password coming from the client.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService); // Set custom UserDetailsService
        provider.setPasswordEncoder(passwordEncoder()); // Set password encoder
        return provider;
    }

}
