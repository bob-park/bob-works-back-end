package org.bobpark.client.domain.user.service;

import org.bobpark.client.domain.user.model.UserResponse;

public interface UserService {

    UserResponse getUser(String userId);

}
