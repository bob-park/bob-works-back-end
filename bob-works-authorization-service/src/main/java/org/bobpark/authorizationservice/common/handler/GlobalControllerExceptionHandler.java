package org.bobpark.authorizationservice.common.handler;

import static org.bobpark.core.model.api.ApiResult.*;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.bobpark.core.model.api.ApiResult;

@Slf4j
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public <T> ApiResult<T> badRequest(IllegalArgumentException e) {
        return error(e);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public <T> ApiResult<T> accessDenied(AccessDeniedException e) {
        return error(e);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public <T> ApiResult<T> internalServerError(Exception e) {
        log.error("Serivce Error - {}", e.getMessage(), e);
        return error(e);
    }
}
