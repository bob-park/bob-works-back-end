package org.bobpark.userservice.domain.user.service.impl;

import java.io.File;
import java.io.IOException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.apache.commons.lang3.StringUtils;

import org.bobpark.core.exception.NotFoundException;
import org.bobpark.core.exception.ServiceRuntimeException;
import org.bobpark.core.model.common.Id;
import org.bobpark.userservice.configure.user.properties.UserProperties;
import org.bobpark.userservice.domain.user.entity.UserAvatar;
import org.bobpark.userservice.domain.user.repository.UserAvatarRepository;
import org.bobpark.userservice.domain.user.service.UserAvatarResourceService;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserAvatarResourceServiceImpl implements UserAvatarResourceService {

    private static final String DEFAULT_AVATAR_FILE_NAME = "default_user_avatar.png";

    private final UserProperties properties;

    private final UserAvatarRepository userAvatarRepository;

    @Override
    public Resource getAvatar(Id<UserAvatar, Long> avatarId) {

        UserAvatar userAvatar =
            userAvatarRepository.findById(avatarId.getValue())
                .orElseThrow(() -> new NotFoundException(avatarId));

        if (StringUtils.isBlank(userAvatar.getAvatarPath())) {
            return getDefaultUserAvatar();
        }

        return getUserAvatar(userAvatar.getAvatarPath());
    }

    private Resource getDefaultUserAvatar() {
        return new ClassPathResource("static/default/" + DEFAULT_AVATAR_FILE_NAME);
    }

    private FileSystemResource getUserAvatar(String avatarPath) {

        String absolutePath = "";

        try {
            absolutePath = properties.getAvatar().getLocation().getFile().getAbsolutePath();
        } catch (IOException e) {
            throw new ServiceRuntimeException(e);
        }

        return new FileSystemResource(absolutePath + File.separatorChar + avatarPath);
    }
}
