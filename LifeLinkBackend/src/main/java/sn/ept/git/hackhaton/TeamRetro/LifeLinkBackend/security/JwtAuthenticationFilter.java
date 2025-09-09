package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.impl.JwtTokenProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final DonorDetailsService donorDetailsService;

    public JwtAuthenticationFilter(
            JwtTokenProvider jwtTokenProvider,
            DonorDetailsService donorDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.donorDetailsService = donorDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        if ("/api/delegate/login-bridge".equals(request.getRequestURI())
            || "/api/delegate/callback".equals(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Get JWT token from request
            String token = getJwtFromRequest(request);

            // Validate token
            if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
                // Get donor ID from token
                Optional<String> donorIdOpt = jwtTokenProvider.getDonorIdFromToken(token);

                if (donorIdOpt.isPresent()) {
                    String donorId = donorIdOpt.get();

                    // Load user details
                    UserDetails userDetails = donorDetailsService.loadUserById(donorId);

                    // Create authentication object
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));

                    // Set authentication in security context
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            logger.error("Could not set user authentication in security context", e);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}