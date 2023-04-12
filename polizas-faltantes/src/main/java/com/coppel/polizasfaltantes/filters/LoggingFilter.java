package com.coppel.polizasfaltantes.filters;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


@Component
public class LoggingFilter extends OncePerRequestFilter {

    private static final String USERNAME_KEY = "username";
    private static final String UUID_KEY = "uuid";

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {

        MDC.put(UUID_KEY, UUID.randomUUID().toString());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            MDC.put(USERNAME_KEY, authentication.getName());
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(UUID_KEY);
            MDC.remove(USERNAME_KEY);
        }
        
    }
    
}
