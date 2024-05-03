package com.fortitest.auditservice.web.filter;

import com.fortitest.auditservice.web.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String roles = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            roles = jwtService.extractRoles(token);
        }

        if (roles != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Iterator<String> roleItr = Arrays.stream(roles.split(",")).iterator();
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            while(roleItr.hasNext()) {
                grantedAuthorities.add(new SimpleGrantedAuthority(roleItr.next()));
            }
            if (jwtService.validateToken(token)) {
                String userId = jwtService.extractUsername(token);
                PreAuthenticatedAuthenticationToken authToken = new PreAuthenticatedAuthenticationToken(userId, null, grantedAuthorities);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}