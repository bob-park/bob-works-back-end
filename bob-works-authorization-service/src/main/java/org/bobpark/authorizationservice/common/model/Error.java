package org.bobpark.authorizationservice.common.model;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Error {

    private final String message;

    public Error(String message) {
        this.message = message;
    }
}
