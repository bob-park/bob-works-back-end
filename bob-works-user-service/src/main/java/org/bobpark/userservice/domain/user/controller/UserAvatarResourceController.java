package org.bobpark.userservice.domain.user.controller;

import static java.net.URLConnection.*;

import lombok.RequiredArgsConstructor;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.bobpark.core.model.common.Id;
import org.bobpark.userservice.domain.user.entity.UserAvatar;
import org.bobpark.userservice.domain.user.service.UserAvatarResourceService;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "user/avatar")
public class UserAvatarResourceController {

    private final UserAvatarResourceService userAvatarResourceService;

    @GetMapping(path = "{avatarId:\\d+}")
    public ResponseEntity<Resource> getAvatar(@PathVariable long avatarId) {

        Resource resource = userAvatarResourceService.getAvatar(Id.of(UserAvatar.class, avatarId));

        String mimeType = guessContentTypeFromName(resource.getFilename());

        return ResponseEntity.ok()
            .contentType(MediaType.valueOf(mimeType))
            .body(resource);
    }
}
