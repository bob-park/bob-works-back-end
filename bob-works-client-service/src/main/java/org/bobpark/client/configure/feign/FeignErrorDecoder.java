package org.bobpark.client.configure.feign;

import static org.apache.commons.lang3.ObjectUtils.*;

import java.io.IOException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

import org.apache.commons.lang3.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Response;
import feign.Response.Body;
import feign.codec.ErrorDecoder;

import org.bobpark.core.exception.NotFoundException;
import org.bobpark.core.model.api.ApiResult;

@Slf4j
@RequiredArgsConstructor
@Component
public class FeignErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;

    @Override
    public Exception decode(String methodKey, Response response) {
        String errorMsg = null;

        try {
            Body body = response.body();

            ApiResult<?> apiResult = objectMapper.readValue(body.asInputStream(), ApiResult.class);

            if (isNotEmpty(apiResult.getError())) {
                errorMsg = apiResult.getError().toString();
            }

        } catch (IOException e) {
            log.warn("Response parse error - {}", e.getMessage());
        }

        switch (response.status()) {
            case 400 -> {
                return new IllegalArgumentException(errorMsg);
            }

            case 401 -> {
                return new AuthenticationServiceException(errorMsg);
            }

            case 404 -> {
                return new NotFoundException(errorMsg);
            }

            default -> {
                return new Exception(errorMsg);
            }
        }

    }
}
