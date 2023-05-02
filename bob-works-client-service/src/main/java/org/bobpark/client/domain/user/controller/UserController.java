package org.bobpark.client.domain.user.controller;

import static org.apache.commons.lang3.math.NumberUtils.*;

import java.util.Map;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.bobpark.client.domain.position.model.PositionResponse;
import org.bobpark.client.domain.team.model.TeamResponse;
import org.bobpark.client.domain.user.model.UserResponse;
import org.bobpark.client.domain.user.service.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @GetMapping(path = "")
    public UserResponse getUser(@AuthenticationPrincipal OidcUser user) {
        return parseToUserResponse(user);

    }

    @GetMapping(path = "vacation")
    public UserResponse getVacation(@AuthenticationPrincipal OidcUser user) {
        return userService.getUser(user.getSubject());
    }

    private UserResponse parseToUserResponse(OidcUser user) {

        Map<String, Object> profile = user.getUserInfo().getClaim("profile");

        Map<String, Object> positionMap = (Map<String, Object>)profile.get("position");
        Map<String, Object> teamMap = (Map<String, Object>)profile.get("team");

        return UserResponse.builder()
            .id(toLong(String.valueOf(profile.get("id"))))
            .email(user.getUserInfo().getEmail())
            .name((String)profile.get("name"))
            .userId(user.getClaimAsString("sub"))
            .position(
                PositionResponse.builder()
                    .id(toLong(String.valueOf(positionMap.get("id"))))
                    .name(String.valueOf(positionMap.get("name")))
                    .build())
            .team(
                TeamResponse.builder()
                    .id(toLong(String.valueOf(teamMap.get("id"))))
                    .name(String.valueOf(teamMap.get("name")))
                    .description(String.valueOf(teamMap.get("description")))
                    .build())
            .build();
    }
}
