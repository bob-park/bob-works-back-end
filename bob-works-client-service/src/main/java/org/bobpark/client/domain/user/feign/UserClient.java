package org.bobpark.client.domain.user.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import org.bobpark.client.domain.user.model.UpdateUserPasswordRequest;
import org.bobpark.client.domain.user.model.UserResponse;

@FeignClient(name = "user-service", path="user")
public interface UserClient {

    // @GetMapping(path = "user")
    @GetMapping(path = "")
    UserResponse getUser(@RequestParam("userId") String userId);

    @PutMapping(path = "{id}/password")
    // @PutMapping(path = "user/{id}/password")
    UserResponse updatePassword(@PathVariable long id, @RequestBody UpdateUserPasswordRequest updateRequest);

}
