package org.bobpark.client.common.handler;

import java.net.ConnectException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.bobpark.core.model.api.ApiResult;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final LogoutHandler logoutHandler;

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({AuthenticationException.class, OAuth2AuthenticationException.class})
    public <T> ApiResult<T> unauthorized(Exception e) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        logoutHandler.logout(request, response, authentication);

        return ApiResult.error(e);
    }

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(ConnectException.class)
    public <T> ApiResult<T> a(Exception e) {
        log.warn("service unavailable - {}", e.getMessage());
        return ApiResult.error("Service Unavailable", e);
    }
}
