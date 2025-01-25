package com.rajesh.practice.identify_service.filter;

import com.rajesh.practice.identify_service.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component

/*
    5. Here we are extending the class OncePerRequestFilter that make our class run once into every call.
    means that if there is request coming and verify and call move to controller, and somehow we call the next
    controller then in that case no need to call this again.
 */
public class JwtFilter  extends OncePerRequestFilter{
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    /*
        6. OncePerRequestFilter class is a abstract class which has a abstract methods doFilterInternal
        that need to override.

        7. Whenever we pass the token through the request it comes with key as : Authorization and
        value starting with: "Bearer "+ jwt token

        8. we need to extract the token and get the userName through it. For checking this process
        open the jwtUtil class : extractUsername() methods as step -9

        10. After getting this userName we get the UserDetails on it and pass it on UsernamePasswordAuthenticationToken object
        And at the end set it to SecurityContextHolder, after all the validation.

        SO, the SecurityContextHolder has all the information about user on userDetail and their authority(roles).
     */

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }
        if (username != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtUtil.validateToken(jwt)) { //for expiry token, is it still valid or has expired
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        response.addHeader("admin","rajesh");
        chain.doFilter(request, response);
    }


}
