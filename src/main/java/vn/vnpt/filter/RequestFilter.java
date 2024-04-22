package vn.vnpt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.HttpMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import vn.vnpt.api.repository.checker.RoleRepo;
import vn.vnpt.api.service.UserService;
import vn.vnpt.authentication.jwt.JwtService;
import vn.vnpt.common.Common;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class RequestFilter implements Filter {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    JwtService jwtService;

    @Autowired
    UserService userService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest originalRequest = (HttpServletRequest) request;

        final String authHeader = originalRequest.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (Common.isNullOrEmpty(authHeader) || !StringUtils.startsWithIgnoreCase(authHeader, "Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUserName(jwt);
        if (!Common.isNullOrEmpty(userEmail)
            && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.userDetailsService()
                    .loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(originalRequest));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }
        }

        var method = originalRequest.getMethod();

        if (method.equals(HttpMethod.POST)) {
            var requestBody = new String(StreamUtils.copyToByteArray(request.getInputStream()));
            var objectMapper = new ObjectMapper();
            var jsonNode = objectMapper.readTree(requestBody);

            chain.doFilter(new RequestWrapper(originalRequest, jsonNode), response);
        } else {
            chain.doFilter(request, response);
        }
    }
}
