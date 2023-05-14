package org.bobpark.client.common.handler;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.bobpark.core.model.api.ApiResult;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({AuthenticationException.class, OAuth2AuthenticationException.class})
    public <T> ApiResult<T> unauthorized(Exception e) {
        return ApiResult.error(e);
    }
}
