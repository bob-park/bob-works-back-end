package org.bobpark.documentservice.domain.user.feign.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.bobpark.documentservice.domain.user.model.UserResponse;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping(path = "user/all")
    List<UserResponse> getUserAll();

    @GetMapping(path = "user")
    UserResponse getUser(@RequestParam("userId") String userId);
}
