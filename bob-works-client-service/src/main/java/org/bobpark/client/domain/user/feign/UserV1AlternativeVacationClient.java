package org.bobpark.client.domain.user.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import org.bobpark.client.domain.user.model.vacation.UserAlternativeVacationResponse;

@FeignClient(name = "user-service", contextId = "user-v1-alternative-vacation-client")
public interface UserV1AlternativeVacationClient {

    @GetMapping(path = "v1/user/{id:\\d+}/alternative/vacation/usable")
    List<UserAlternativeVacationResponse> getUsableList(@PathVariable long id);

    @GetMapping(path = "v1/user/{id:\\d+}/alternative/vacation/find/ids")
    List<UserAlternativeVacationResponse> findAllByIds(@PathVariable long id,
        @RequestParam List<Long> alternativeVacationIds);

    @GetMapping(path = "v1/user/{id:\\d+}/alternative/vacation/all")
    List<UserAlternativeVacationResponse> getAll(@PathVariable long id);
}
