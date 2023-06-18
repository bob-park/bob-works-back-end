package org.bobpark.userservice.domain.user.controller.vacation;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.bobpark.core.model.common.Id;
import org.bobpark.userservice.domain.user.entity.User;
import org.bobpark.userservice.domain.user.model.UserResponse;
import org.bobpark.userservice.domain.user.model.vacation.AddAlternativeVacationRequest;
import org.bobpark.userservice.domain.user.service.vacation.UserAlternativeVacationService;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/user/{id:\\d+}/alternative/vacation")
public class UserAlternativeVacationV1Controller {

    private final UserAlternativeVacationService userAlternativeVacationService;

    @PostMapping(path = "")
    public UserResponse addAlternativeVacation(@PathVariable long id,
        @RequestBody AddAlternativeVacationRequest addRequest) {
        return userAlternativeVacationService.addAlternativeVacation(Id.of(User.class, id), addRequest);
    }
}
