package org.bobpark.client.domain.user.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.bobpark.client.domain.user.model.UserResponse;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping(path = "user")
    UserResponse getUser(@RequestParam("userId") String userId);

}
