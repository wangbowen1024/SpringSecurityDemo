package com.securitydemo.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.securitydemo.enums.Status;
import com.securitydemo.utils.Result;
import com.securitydemo.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 鉴权失败处理器
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        final Result result = Result.error(Status.FORBIDDEN);
        final ObjectMapper objectMapper = new ObjectMapper();
        WebUtils.renderString(response, objectMapper.writeValueAsString(result));
    }
}
