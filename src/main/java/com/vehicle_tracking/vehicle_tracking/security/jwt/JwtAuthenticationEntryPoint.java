package com.vehicle_tracking.vehicle_tracking.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehicle_tracking.vehicle_tracking.dtos.response.SimpleErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper(); // Reusable ObjectMapper

    @Override
    public void commence(HttpServletRequest request, // Corrected import
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        log.error("Responding with unauthorized error. Path: {}. Message: {}", request.getRequestURI(), authException.getMessage());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); // Use MediaType constant

        // Create a simple error response
        SimpleErrorResponse errorResponse = new SimpleErrorResponse(
                false,
                "Authentication Failed: " + authException.getMessage()
                // Or a more generic message if you prefer:
                // "You are not authorized to access this resource."
        );

        // Write the JSON response directly to the output stream
        OutputStream out = response.getOutputStream();
        objectMapper.writeValue(out, errorResponse);
        out.flush(); // Ensure the response is sent
    }
}