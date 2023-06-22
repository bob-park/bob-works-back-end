package org.bobpark.client.domain.user.controller.vacation;

import static org.bobpark.client.common.utils.CommonUtils.*;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.bobpark.client.domain.user.model.UserResponse;
import org.bobpark.client.domain.user.model.vacation.UserAlternativeVacation;
import org.bobpark.client.domain.user.service.vacation.UserAlternativeVacationService;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "user/alternative/vacation")
public class UserAlternativeVacationController {

    private final UserAlternativeVacationService userAlternativeVacationService;

    @GetMapping(path = "usable")
    public List<UserAlternativeVacation> getUsable(@AuthenticationPrincipal OidcUser oidcUser) {

        UserResponse userResponse = parseToUserResponse(oidcUser);

        return userAlternativeVacationService.getUsableList(userResponse.id());
    }
}
