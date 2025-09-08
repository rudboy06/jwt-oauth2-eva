package com.example.eval.security;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Collections;

/**
 * Filtro que:
 *  - Lee Authorization: Bearer <token>
 *  - Valida el JWT y establece la autenticacion en el SecurityContext
 */
@Component
public class JwtAuthFilter implements Filter {

    private final JwtService jwtService;

    public JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest http = (HttpServletRequest) request;

        String header = http.getHeader("Authorization");
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                String subject = jwtService.validateAndGetSubject(token);

                UserDetails userDetails = User.withUsername(subject)
                        .password("") // no se usa en este flujo
                        .authorities(Collections.emptyList())
                        .build();

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(http));
                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (Exception ignored) {
                // Token invalido/expirado -> no autenticamos, sigue la cadena
            }
        }

        chain.doFilter(request, response);
    }
}
