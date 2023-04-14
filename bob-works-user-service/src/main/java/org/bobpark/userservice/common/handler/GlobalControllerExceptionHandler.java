package org.bobpark.userservice.common.handler;

import static org.bobpark.core.model.api.ApiResult.*;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.bobpark.core.exception.NotFoundException;
import org.bobpark.core.model.api.ApiResult;

@Slf4j
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class, HttpMessageNotReadableException.class})
    public <T> ApiResult<T> badRequest(Exception e) {
        return error("Bad request", e);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public <T> ApiResult<T> accessDenied(AccessDeniedException e) {
        return error("Forbidden", e);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public <T> ApiResult<T> notFound(NotFoundException e) {
        log.warn("not found - {}", e.getMessage());
        return error("Not found", e);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public <T> ApiResult<T> internalServerError(Exception e) {
        log.error("Service Error - {}", e.getMessage(), e);
        return error("Service error", e);
    }
}
