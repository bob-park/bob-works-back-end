package org.bobpark.authorizationservice.domain.user.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.bobpark.authorizationservice.domain.user.model.UserResponse;

@FeignClient(name = "user-service", contextId = "user-service")
public interface UserServiceClient {

    @GetMapping(path = "user")
    UserResponse getUser(@RequestParam String userId);

}
