package org.bobpark.userservice.domain.user.controller.vacation;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.bobpark.core.model.common.Id;
import org.bobpark.userservice.domain.user.entity.User;
import org.bobpark.userservice.domain.user.model.UserResponse;
import org.bobpark.userservice.domain.user.model.vacation.AddTotalAlternativeVacationRequest;
import org.bobpark.userservice.domain.user.model.vacation.CancelUserVacationRequest;
import org.bobpark.userservice.domain.user.model.vacation.UseUserVacationRequest;
import org.bobpark.userservice.domain.user.service.vacation.UserVacationService;

@RequiredArgsConstructor
@RestController
@RequestMapping("user/{id:\\d+}/vacation")
public class UserVacationController {

    private final UserVacationService userVacationService;

    @PutMapping(path = "use")
    public UserResponse useVacation(@PathVariable long id, @RequestBody UseUserVacationRequest useVacationRequest) {
        return userVacationService.useVacation(Id.of(User.class, id), useVacationRequest);
    }

    @PutMapping(path = "cancel")
    public UserResponse cancelVacation(@PathVariable long id,
        @RequestBody CancelUserVacationRequest cancelVacationRequest) {
        return userVacationService.cancelVacation(Id.of(User.class, id), cancelVacationRequest);
    }

    @PostMapping(path = "alternative/increase")
    public UserResponse addAlternativeVacation(@PathVariable long id,
        @RequestBody AddTotalAlternativeVacationRequest addRequest) {
        return userVacationService.addTotalAlternativeVacation(Id.of(User.class, id), addRequest);
    }

}
