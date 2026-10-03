package org.example.telemetryapi.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.telemetryapi.exceptions.UnauthorizedException;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ApiKeyInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(ApiKeyInterceptor.class);
    private static final String API_KEY_HEADER = "X-API-KEY";

    @Value("${api.security.telemetry-key}")
    private String configuredApiKey;

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        String requestApiKey = request.getHeader(API_KEY_HEADER);

        if (requestApiKey == null || requestApiKey.trim().isEmpty()) {
            log.warn("API key is missing in the request header");
            throw new UnauthorizedException("API key is required");
        }

        if (!configuredApiKey.equals(requestApiKey)) {
            log.warn("Provided API key is invalid");
            throw new UnauthorizedException("Invalid API key");
        }

        log.debug("API key validated successfully");
        return true;

    }

}
