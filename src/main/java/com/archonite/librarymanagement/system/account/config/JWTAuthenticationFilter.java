package com.archonite.librarymanagement.system.account.config;

import com.archonite.librarymanagement.system.account.util.JwtUtility;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtility jwtUtility;


    private final CustomUserDetailService userDetailsService;

    public JWTAuthenticationFilter(JwtUtility jwtUtility, CustomUserDetailService userDetailsService) {
        this.jwtUtility = jwtUtility;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                jwtUtility.validateToken(token);
                String username = jwtUtility.extractUsername(token);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

//                String roleFromJwt = jwtUtility.extractRole(token); // e.g., "ADMIN"
//                List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + roleFromJwt));

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (JwtException ex) {
                System.out.println("Invalid JWT: " + ex.getMessage());
                writeErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Token has expired, Please sign-in again!");
                return;
            }
        }

        filterChain.doFilter(request, response);


    }

    private void writeErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        if (response.isCommitted()) {
            return;
        }
        response.resetBuffer();
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String body = String.format(
                "{\"error\":\"%s\",\"status\":%d,\"timestamp\":\"%s\"}",
                message,
                status,
                java.time.LocalDateTime.now()
        );

        response.getWriter().write(body);
        response.flushBuffer();
    }
}
