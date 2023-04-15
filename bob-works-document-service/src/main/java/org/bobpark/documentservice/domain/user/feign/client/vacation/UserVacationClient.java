package org.bobpark.documentservice.domain.user.feign.client.vacation;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.bobpark.documentservice.domain.user.model.UserResponse;
import org.bobpark.documentservice.domain.user.model.vacation.CancelUserVacationRequest;
import org.bobpark.documentservice.domain.user.model.vacation.UseUserVacationRequest;

@FeignClient(name = "user-service")
public interface UserVacationClient {

    @PutMapping(path = "user/{id:\\d+}/vacation/use")
    UserResponse useVacation(@PathVariable long id, @RequestBody UseUserVacationRequest useRequest);

    @PutMapping(path = "user/{id:\\d+}/vacation/cancel")
    UserResponse cancelVacation(@PathVariable long id, @RequestBody CancelUserVacationRequest cancelRequest);
}
