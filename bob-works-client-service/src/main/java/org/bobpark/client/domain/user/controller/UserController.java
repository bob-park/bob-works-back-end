package org.bobpark.client.domain.user.controller;

import static org.apache.commons.lang3.math.NumberUtils.*;

import java.util.Map;

import lombok.RequiredArgsConstructor;

import org.springframework.core.io.Resource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.bobpark.client.domain.position.model.PositionResponse;
import org.bobpark.client.domain.team.model.TeamResponse;
import org.bobpark.client.domain.user.model.UpdateUserAvatarRequest;
import org.bobpark.client.domain.user.model.UpdateUserDocumentSignatureRequest;
import org.bobpark.client.domain.user.model.UpdateUserPasswordRequest;
import org.bobpark.client.domain.user.model.UserResponse;
import org.bobpark.client.domain.user.model.vacation.UserVacationResponse;
import org.bobpark.client.domain.user.service.UserService;
import org.bobpark.core.exception.ServiceRuntimeException;

@RequiredArgsConstructor
@RestController
@RequestMapping("user")
public class UserController {

    private final ObjectMapper mapper;
    private final UserService userService;

    @GetMapping(path = "")
    public UserResponse getUser(@AuthenticationPrincipal OidcUser user) {
        return parseToUserResponse(user);

    }

    @GetMapping(path = "vacation")
    public UserResponse getVacation(@AuthenticationPrincipal OidcUser user) {
        return userService.getUser(user.getSubject());
    }

    @PutMapping(path = "password")
    public UserResponse updatePassword(@AuthenticationPrincipal OidcUser user,
        @RequestBody UpdateUserPasswordRequest updateRequest) {

        UserResponse userinfo = parseToUserResponse(user);

        return userService.updatePassword(userinfo.id(), updateRequest);
    }

    @PostMapping(path = "avatar")
    public UserResponse updateAvatar(@AuthenticationPrincipal OidcUser user,
        UpdateUserAvatarRequest updateRequest) {
        UserResponse userinfo = parseToUserResponse(user);

        return userService.updateAvatar(userinfo.id(), updateRequest);
    }

    @GetMapping(path = "{id}/document/signature")
    public Resource getDocumentSignature(@PathVariable long id) {
        return userService.getDocumentSignature(id);
    }

    @PostMapping(path = "{id}/document/signature")
    public UserResponse updateSignature(@PathVariable long id, UpdateUserDocumentSignatureRequest updateRequest) {
        return userService.updateSignature(id, updateRequest);
    }

    private UserResponse parseToUserResponse(OidcUser user) {

        Map<String, Object> profile = user.getUserInfo().getClaim("profile");

        Map<String, Object> positionMap = (Map<String, Object>)profile.get("position");
        Map<String, Object> teamMap = (Map<String, Object>)profile.get("team");

        UserVacationResponse userVacationResponse = null;

        try {
            String nowVacationStr = mapper.writeValueAsString(profile.get("nowVacation"));

            userVacationResponse = mapper.readValue(nowVacationStr, UserVacationResponse.class);

        } catch (JsonProcessingException e) {
            throw new ServiceRuntimeException(e);
        }

        return UserResponse.builder()
            .id(toLong(String.valueOf(profile.get("id"))))
            .email(user.getUserInfo().getEmail())
            .name((String)profile.get("name"))
            .userId(user.getClaimAsString("sub"))
            .avatar((String)profile.get("avatar"))
            .nowVacation(userVacationResponse)
            .position(
                PositionResponse.builder()
                    .id(toLong(String.valueOf(positionMap.get("id"))))
                    .name(String.valueOf(positionMap.get("name")))
                    .build())
            .team(teamMap != null ?
                TeamResponse.builder()
                    .id(toLong(String.valueOf(teamMap.get("id"))))
                    .name(String.valueOf(teamMap.get("name")))
                    .description(String.valueOf(teamMap.get("description")))
                    .build() : null)
            .build();
    }
}
