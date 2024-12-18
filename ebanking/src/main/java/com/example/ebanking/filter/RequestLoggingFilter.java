package com.example.ebanking.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;

@Component
public class RequestLoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // Log only for our specific endpoint
        if (httpRequest.getRequestURI().contains("/api/v1/test/getData")) {
            StringBuilder requestBody = new StringBuilder();
            BufferedReader reader = httpRequest.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
            if(requestBody.toString() != null){
                HttpSession session = httpRequest.getSession();
                session.setAttribute("token" , requestBody.toString());
            }else{
                try {
                    throw new Exception("Request body is null");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        }

        chain.doFilter(request, response);
    }
}