package org.bobpark.client.domain.user.service;

import org.bobpark.client.domain.user.model.UpdateUserAvatarRequest;
import org.bobpark.client.domain.user.model.UpdateUserPasswordRequest;
import org.bobpark.client.domain.user.model.UserResponse;

public interface UserService {

    UserResponse getUser(String userId);

    UserResponse updatePassword(long id, UpdateUserPasswordRequest updateRequest);

    UserResponse updateAvatar(long id, UpdateUserAvatarRequest updateRequest);

}
