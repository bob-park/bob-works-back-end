package org.bobpark.userservice.domain.user.service;

import java.util.List;

import org.bobpark.core.model.common.Id;
import org.bobpark.userservice.domain.user.entity.User;
import org.bobpark.userservice.domain.user.model.SearchUserRequest;
import org.bobpark.userservice.domain.user.model.UpdateUserPasswordRequest;
import org.bobpark.userservice.domain.user.model.UserResponse;

public interface UserService {

    UserResponse getUserById(Id<User, Long> id);

    UserResponse getUser(Id<User, String> userId);

    UserResponse updatePassword(Id<User, Long> userId, UpdateUserPasswordRequest updateRequest);

    List<UserResponse> getUsersAll();

    List<UserResponse> searchUsers(SearchUserRequest searchRequest);

}
