package org.bobpark.client.domain.user.service;

import java.util.List;

import org.springframework.core.io.Resource;

import org.bobpark.client.domain.user.model.UpdateUserAvatarRequest;
import org.bobpark.client.domain.user.model.UpdateUserDocumentSignatureRequest;
import org.bobpark.client.domain.user.model.UpdateUserPasswordRequest;
import org.bobpark.client.domain.user.model.UserResponse;

public interface UserService {

    UserResponse getUser(String userId);

    List<UserResponse> getAllUser();

    UserResponse updatePassword(long id, UpdateUserPasswordRequest updateRequest);

    UserResponse updateAvatar(long id, UpdateUserAvatarRequest updateRequest);

    Resource getDocumentSignature(long id);

    UserResponse updateSignature(long id, UpdateUserDocumentSignatureRequest updateRequest);

}
