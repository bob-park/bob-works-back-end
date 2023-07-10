package org.bobpark.documentservice.domain.user.feign.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import org.bobpark.documentservice.domain.user.model.UserResponse;
import org.bobpark.documentservice.domain.user.model.vacation.AddAlternativeVacationRequest;
import org.bobpark.documentservice.domain.user.model.vacation.CancelUserVacationRequest;
import org.bobpark.documentservice.domain.user.model.vacation.UseUserVacationRequest;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping(path = "user/all")
    List<UserResponse> getUserAll();

    @GetMapping(path = "user")
    UserResponse getUser(@RequestParam("userId") String userId);

    @PutMapping(path = "user/{id:\\d+}/vacation/use")
    UserResponse useVacation(@PathVariable long id, @RequestBody UseUserVacationRequest useRequest);

    @PutMapping(path = "user/{id:\\d+}/vacation/cancel")
    UserResponse cancelVacation(@PathVariable long id, @RequestBody CancelUserVacationRequest cancelRequest);

    @PostMapping(path = "v1/user/{id:\\d+}/alternative/vacation")
    UserResponse addAlternativeVacation(@PathVariable long id, @RequestBody AddAlternativeVacationRequest addRequest);

}
