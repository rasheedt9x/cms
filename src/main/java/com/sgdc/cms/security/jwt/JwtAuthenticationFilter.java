package com.sgdc.cms.security.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sgdc.cms.services.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * JwtAuthenticationFilter
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final TokenService tokenService;
    
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService, TokenService ts) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.tokenService = ts;
    }

@Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
    String token = jwtTokenProvider.extractToken(request);
    if (token != null) {
        System.out.println("Extracted Token: " + token);
        if (jwtTokenProvider.validateToken(token) && !tokenService.isTokenBlacklisted(token)) {
            String username = jwtTokenProvider.getUsernameFromToken(token);
            System.out.println("Authenticated User: " + username);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (userDetails != null) {
                UsernamePasswordAuthenticationToken upawt = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                upawt.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                System.out.println(userDetails.getAuthorities().toString());
                SecurityContextHolder.getContext().setAuthentication(upawt);
            }
        } else {
            System.out.println("Invalid Token");
        }
    }
    filterChain.doFilter(request, response);
}




    // @Override
    // protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    //         throws ServletException, IOException {
    //     String token = jwtTokenProvider.extractToken(request);
    //     if (token != null && jwtTokenProvider.validateToken(token)) {
    //         String username = jwtTokenProvider.getUsernameFromToken(token);
    //         UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    //         if (userDetails != null) {

    //             UsernamePasswordAuthenticationToken upawt = new UsernamePasswordAuthenticationToken(
    //                     userDetails, null, userDetails.getAuthorities());

    //             upawt.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    //             SecurityContextHolder.getContext().setAuthentication(upawt);
    //         }
    //     }
    //     filterChain.doFilter(request, response);

    // }

}
