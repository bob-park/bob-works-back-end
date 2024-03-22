package org.bobpark.userservice.domain.user.service;

import org.springframework.core.io.Resource;

import org.bobpark.core.model.common.Id;
import org.bobpark.userservice.domain.user.entity.User;
import org.bobpark.userservice.domain.user.entity.UserAvatar;

public interface UserAvatarResourceService {

    Resource getAvatar(Id<UserAvatar, Long> avatarId);

    Resource getUserAvatar(Id<User, Long> userId);
}
