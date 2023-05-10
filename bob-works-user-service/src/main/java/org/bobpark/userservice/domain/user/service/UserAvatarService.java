package org.bobpark.userservice.domain.user.service;

import org.bobpark.core.model.common.Id;
import org.bobpark.userservice.domain.user.entity.User;
import org.bobpark.userservice.domain.user.model.UpdateUserAvatarRequest;
import org.bobpark.userservice.domain.user.model.UserResponse;

public interface UserAvatarService {

    UserResponse updateAvatar(Id<User, Long> userId, UpdateUserAvatarRequest updateRequest);

}
