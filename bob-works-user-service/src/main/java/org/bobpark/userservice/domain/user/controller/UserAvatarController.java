package org.bobpark.userservice.domain.user.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import org.bobpark.core.model.common.Id;
import org.bobpark.userservice.domain.user.entity.User;
import org.bobpark.userservice.domain.user.model.UserResponse;
import org.bobpark.userservice.domain.user.service.UserAvatarResourceService;
import org.bobpark.userservice.domain.user.service.UserAvatarService;

@RequiredArgsConstructor
@RestController
@RequestMapping("user/{userId:\\d+}/avatar")
public class UserAvatarController {

    private final UserAvatarService userAvatarService;
    private final UserAvatarResourceService userAvatarResourceService;

    @GetMapping(path = "")
    public Resource getUserAvatar(@PathVariable long userId) {
        return userAvatarResourceService.getUserAvatar(Id.of(User.class, userId));
    }

    @PostMapping(path = "")
    public UserResponse updateAvatar(@PathVariable long userId, @RequestPart("avatar") MultipartFile avatar) {
        return userAvatarService.updateAvatar(Id.of(User.class, userId), avatar);
    }
}
