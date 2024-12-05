package com.example.ebanking.filter;


import com.example.ebanking.service.user.CustomUserDetailsService;
import com.example.ebanking.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j  // Add this annotation
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final HttpSession session;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String requestPath = request.getRequestURI();
        log.info("Processing request to: {}", requestPath);

        // Skip authentication for public endpoints
        if (requestPath.startsWith("/api/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // First check session-based token
            String sessionToken = session.getAttribute("ticket") != null ?
                    session.getAttribute("ticket").toString() : null;

            // Then check header-based token
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            final String username;

            // Log authentication attempt details
            log.info("Session token present: {}", sessionToken != null);
            log.info("Auth header present: {}", authHeader != null);

            // Determine which token to use
            if (sessionToken != null) {
                jwt = sessionToken;
                log.info("Using session token");
            } else if (authHeader != null && authHeader.startsWith("Bearer ")) {
                jwt = authHeader.substring(7);
                log.info("Using Bearer token");
            } else {
                log.warn("No authentication token found");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("No authentication token provided");
                return;  // Stop the filter chain if no token is present
            }

            // Extract username from JWT
            username = jwtUtil.extractUserName(jwt);
            log.info("Extracted username: {}", username);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (!jwtUtil.isTokenExpired(jwt)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    log.info("User successfully authenticated: {}", username);
                } else {
                    log.warn("Token is expired");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Token is expired");
                    return;
                }
            }
        } catch (Exception e) {
            log.error("Authentication error: ", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Authentication failed: " + e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }
}