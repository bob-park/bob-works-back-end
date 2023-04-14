package org.bobpark.userservice.domain.user.service;

import java.util.List;

import org.bobpark.core.model.common.Id;
import org.bobpark.userservice.domain.user.entity.User;
import org.bobpark.userservice.domain.user.model.UserResponse;

public interface UserService {

    UserResponse getUser(Id<User, String> userId);

    List<UserResponse> getUserAll();

}
