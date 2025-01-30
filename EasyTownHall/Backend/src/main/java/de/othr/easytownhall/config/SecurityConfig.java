package de.othr.easytownhall.config;


import de.othr.easytownhall.models.enums.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UsersAuthenticationEntryPoint usersAuthenticationEntryPoint;
    private final UsersAuthenticationProvider usersAuthenticationProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(customizer -> customizer.authenticationEntryPoint(usersAuthenticationEntryPoint))
                .addFilterBefore(new JwtAuthFilter(usersAuthenticationProvider), BasicAuthenticationFilter.class)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v1/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v1/auth/register").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/news/getRecent").permitAll()

                //nach testen rauswerfen
                .requestMatchers(HttpMethod.GET,"/api/v1/workschedule/**").permitAll()
                .requestMatchers(HttpMethod.POST,"/api/v1/workschedule/**").permitAll()

                .requestMatchers(HttpMethod.GET,"/api/v1/department/**").permitAll()
                .requestMatchers(HttpMethod.POST,"/api/v1/department/**").permitAll()
                //

                        .requestMatchers(HttpMethod.GET,"/api/v1/nina/covidticker").permitAll()
                .requestMatchers(HttpMethod.GET,"/api/v1/nina/covidticker/data").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/weather").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/footballStats").permitAll()
                .requestMatchers(HttpMethod.POST,"/api/v1/application/{id}/submit").hasRole(RoleEnum.CITIZEN.getDescription())
                .requestMatchers(HttpMethod.GET, "/api/v1/profil/{id}").hasRole(RoleEnum.CITIZEN.getDescription())
                .requestMatchers(HttpMethod.PUT, "/api/v1/profil/{id}").hasRole(RoleEnum.CITIZEN.getDescription())
                .requestMatchers(HttpMethod.DELETE, "/api/v1/profil/{id}").hasRole(RoleEnum.CITIZEN.getDescription())
                .requestMatchers(HttpMethod.GET, "/api/v1/profil/citizen/{id}").hasRole(RoleEnum.CITIZEN.getDescription())
                .requestMatchers(HttpMethod.PUT, "/api/v1/profil/citizen/{id}").hasRole(RoleEnum.CITIZEN.getDescription())
                .requestMatchers(HttpMethod.GET,"/api/v1/application//completted/{id}/**").hasRole(RoleEnum.CITIZEN.getDescription())
                .requestMatchers(HttpMethod.GET,"/api/v1/application/submitted").hasRole(RoleEnum.SERVICEWORKER.getDescription())
                .requestMatchers(HttpMethod.GET,"/api/v1/application/submitted/{id}").hasRole(RoleEnum.CITIZEN.getDescription())
                .requestMatchers(HttpMethod.POST,"/api/v1/application/submitted/{id}/accept").hasRole(RoleEnum.SERVICEWORKER.getDescription())
                .requestMatchers(HttpMethod.POST,"/api/v1/application-forms").hasRole(RoleEnum.SERVICEWORKER.getDescription())
                .requestMatchers(HttpMethod.PUT,"/api/v1/application-forms/{id}").hasRole(RoleEnum.SERVICEWORKER.getDescription())
                .requestMatchers(HttpMethod.PUT,"/api/v1/application-forms/archive/{id}").hasRole(RoleEnum.SERVICEWORKER.getDescription())
                .requestMatchers(HttpMethod.GET,"/api/v1/test/hello").hasRole(RoleEnum.ADMIN.getDescription())


                .anyRequest().authenticated())
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())); // Frames für H2 erlauben
        return http.build();
    }

    // aktuell doppelt drinnen, weil ich keinen plan hab, warum der das nicht zu lässt
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200")); // Angular-Frontend-URL
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public SecurityExpressionHandler<FilterInvocation> customWebSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy());
        return expressionHandler;
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_ADMIN > ROLE_SERVICEWORKER \n ROLE_SERVICEWORKER > ROLE_CITIZEN";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }
}