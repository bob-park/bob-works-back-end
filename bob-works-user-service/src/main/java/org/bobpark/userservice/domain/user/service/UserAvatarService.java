package org.bobpark.userservice.domain.user.service;

import org.bobpark.userservice.domain.user.model.UpdateUserAvatarRequest;
import org.bobpark.userservice.domain.user.model.UserResponse;

public interface UserAvatarService {

    UserResponse updateAvatar(UpdateUserAvatarRequest updateRequest);

}
