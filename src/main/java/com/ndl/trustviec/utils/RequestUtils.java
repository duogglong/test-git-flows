package com.ndl.trustviec.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;


public class RequestUtils {

    public static String getToken(HttpServletRequest httpServletRequest) {
        String token = null;
        String authorizationHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.isEmpty(authorizationHeader)) {
            token = authorizationHeader.substring("Bearer ".length());
        }
        return token;
    }

}
