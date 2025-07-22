package com.ndl.trustviec.config.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ndl.trustviec.common.constants.ApiList;
import com.ndl.trustviec.common.error.ErrorConstants;
import com.ndl.trustviec.common.exception.CommonException;
import com.ndl.trustviec.dto.response.ApiResponse;
import com.ndl.trustviec.utils.JwtUtils;
import com.ndl.trustviec.utils.RequestUtils;
import com.ndl.trustviec.utils.StringUtils;
import com.ndl.trustviec.utils.system.SystemContextHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {
    private final JwtUtils jwtUtil;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        try {
            // Getting request information without the hostname.
            String uri = request.getRequestURI();

            String token = RequestUtils.getToken(request);

            boolean isPublic = ApiList.PUBLIC_URLS.stream()
                    .map(url -> url.replace("/**", ""))
                    .anyMatch(uri::contains);

            if (isPublic) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else if (!StringUtils.isEmpty(token) && jwtUtil.isTokenValid(token)) {
                // ✅ Set security context
                UserDetails userDetails = jwtUtil.getUserDetails(token);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                SystemContextHolder.create(jwtUtil, token);
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                throw CommonException.create(HttpStatus.UNAUTHORIZED).code(ErrorConstants.UNAUTHORIZED);
            }
        } catch (Exception e) {
            logger.error(e);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), ApiResponse.unAuthentication(e.getMessage()));
        }
    }
}
