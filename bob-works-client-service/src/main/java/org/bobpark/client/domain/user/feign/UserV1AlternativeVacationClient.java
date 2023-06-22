package org.bobpark.client.domain.user.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.bobpark.client.domain.user.model.vacation.UserAlternativeVacation;

@FeignClient(name = "user-service", contextId = "user-v1-alternative-vacation-client")
public interface UserV1AlternativeVacationClient {

    @GetMapping(path = "v1/user/{id:\\d+}/alternative/vacation/usable")
    List<UserAlternativeVacation> getUsableList(@PathVariable long id);
}
