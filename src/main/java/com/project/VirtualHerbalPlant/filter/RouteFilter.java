package com.project.VirtualHerbalPlant.filter;

import com.project.VirtualHerbalPlant.config.UserPrincipal;
import com.project.VirtualHerbalPlant.entity.UserEntity;
import com.project.VirtualHerbalPlant.service.JWTService;
import com.project.VirtualHerbalPlant.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RouteFilter extends OncePerRequestFilter {

    private final UserService userService;


    private final JWTService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = null;
        String username = null;
        String userId = null;
        String role = null;

        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);

            if (jwtService.validateToken(token)) { // Validate without UserDetails
                username = jwtService.extractUserName(token);
                userId = jwtService.extractUserId(token);     // Extract from JWT
                role = jwtService.extractRole(token);         // Extract from JWT

                UserEntity userById = userService.findUserById(new ObjectId(userId));



                // Create UserPrincipal from JWT data - NO database call
                List<GrantedAuthority> authorities = Collections.singletonList(
                        new SimpleGrantedAuthority(role)
                );

                UserPrincipal userPrincipal = new UserPrincipal(
                        userId, username, null, role, authorities);


                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userPrincipal, null, authorities
                        );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }

}

