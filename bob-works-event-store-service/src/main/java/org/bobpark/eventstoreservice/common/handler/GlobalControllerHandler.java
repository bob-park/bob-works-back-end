package org.bobpark.eventstoreservice.common.handler;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.bobpark.core.model.api.ApiResult;
import org.bobpark.eventstoreservice.exception.AlreadyExecutedEventException;
import org.bobpark.eventstoreservice.exception.InvalidEventStatusException;

@Slf4j
@RestControllerAdvice
public class GlobalControllerHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({AlreadyExecutedEventException.class, InvalidEventStatusException.class})
    public <T> ApiResult<T> badRequest(Exception e) {
        return ApiResult.error(e);
    }

}
