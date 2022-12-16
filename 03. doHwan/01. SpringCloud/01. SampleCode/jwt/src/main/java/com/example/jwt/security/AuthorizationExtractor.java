package com.example.jwt.security;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/* Bearer 토큰 추출 하는 class */
@Component
public class AuthorizationExtractor {
    public static final String AUTHORIZATION = "Authorization";

    public String extract(HttpServletRequest request, String type) {
        Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
        while(headers.hasMoreElements()) {
            String value = headers.nextElement();
            if(value.toLowerCase().startsWith(type.toLowerCase())) {
                return value.substring(type.length());
            }
        }

        return Strings.EMPTY;
    }
}
