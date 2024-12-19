package de.othr.securityproject.config.jwt;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import de.othr.securityproject.service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


//additional filter to intercept the request and check if there is a token on it.
//If there is, then it will try to validate the token.
// if there is not, the user should authenticate himself.


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(
    		jakarta.servlet.http.HttpServletRequest request, 
    		HttpServletResponse response, 
    		FilterChain chain)
            throws jakarta.servlet.ServletException, IOException {

    	//this comes from the header of the request
        final String authorizationHeader = request.getHeader("Authorization");

        
        String username = null;
        String jwt = null;

        //if the authorization content in the header is not null and starts with "Bearer "...
        if (authorizationHeader != null && authorizationHeader.startsWith(jwtService.getPREFIX())) {
            jwt = authorizationHeader.substring(jwtService.getPREFIX().length());
            username = jwtService.extractUsername(jwt);
        }

        //if the user is not empty and the user is not yet authenticated, we will update the security context holder with the token
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

        	//trying to find the user in the DB
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            //if the token is valid, we will proceed with the login...
            if (jwtService.validateToken(jwt, userDetails)) {

            
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
               
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }//continue with the chain of filter
        
        chain.doFilter(request, response);
    }

}
