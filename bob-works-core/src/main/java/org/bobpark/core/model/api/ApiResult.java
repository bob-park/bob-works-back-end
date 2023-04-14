package org.bobpark.core.model.api;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ApiResult<T> {

    private T result;
    private Error error;

    private ApiResult() {
    }

    private ApiResult(T result, Error error) {
        this.result = result;
        this.error = error;
    }

    public static <T> ApiResult<T> ok(T result) {
        return new ApiResult<>(result, null);
    }

    public static <T> ApiResult<T> error(Throwable e) {
        return new ApiResult<>(null, new Error(e.getMessage(), null));
    }

    public static <T> ApiResult<T> error(String msg, Throwable e){
        return new ApiResult<>(null, new Error(msg, e.getMessage()));
    }

}
