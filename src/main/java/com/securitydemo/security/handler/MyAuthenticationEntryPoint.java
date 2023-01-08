package com.securitydemo.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.securitydemo.enums.Status;
import com.securitydemo.utils.Result;
import com.securitydemo.utils.WebUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义认证失败处理
 */
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        final Result result = Result.error(Status.UNAUTHORIZED);
        final ObjectMapper objectMapper = new ObjectMapper();
        WebUtils.renderString(response, objectMapper.writeValueAsString(result));
    }
}
