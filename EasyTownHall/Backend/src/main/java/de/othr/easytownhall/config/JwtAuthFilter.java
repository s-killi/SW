package de.othr.easytownhall.config;


import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import org.springframework.http.HttpHeaders;
import java.util.*;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UsersAuthenticationProvider usersAuthenticationProvider;

    // Liste der freigegebenen Endpunkte
    private static final List<String> EXCLUDED_PATHS = List.of(
            "/api/v1/auth/login",
            "/api/v1/auth/register",
            "/api/v1/nina/covidticker"
    );

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        if (EXCLUDED_PATHS.contains(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);


        if(header != null){
            String[] authElements = header.split(" ");
            if(authElements.length == 2
                    && authElements[0].equals("Bearer")){
                try {

                    SecurityContextHolder.getContext().setAuthentication(usersAuthenticationProvider.validateToken(authElements[1]));

                } catch (TokenExpiredException e) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    //response.getWriter().write("Token is expired. Please log in again");
                }
                catch (RuntimeException e){
                    SecurityContextHolder.clearContext();
                    throw e;
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
