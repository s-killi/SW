package de.othr.jwtClient.config;

import java.io.IOException;


import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
    		org.springframework.security.core.Authentication authentication) throws IOException, ServletException {
        // Handle the successful login here
        response.sendRedirect("/");
    }
}
