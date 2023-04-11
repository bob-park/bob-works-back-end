package org.bobpark.authorizationservice.common.model;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ApiResult<T> {

    private final T result;
    private final Error error;

    private ApiResult(T result, Error error) {
        this.result = result;
        this.error = error;
    }

    public static <T> ApiResult<T> ok(T result) {
        return new ApiResult<>(result, null);
    }

    public static <T> ApiResult<T> error(Throwable e) {
        return new ApiResult<>(null, new Error(e.getMessage()));
    }
}
