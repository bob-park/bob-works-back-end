package org.bobpark.client.domain.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import org.bobpark.client.domain.user.feign.UserClient;
import org.bobpark.client.domain.user.model.UpdateUserAvatarRequest;
import org.bobpark.client.domain.user.model.UpdateUserDocumentSignatureRequest;
import org.bobpark.client.domain.user.model.UpdateUserPasswordRequest;
import org.bobpark.client.domain.user.model.UserResponse;
import org.bobpark.client.domain.user.service.UserService;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserClient userClient;

    @Override
    public UserResponse getUser(String userId) {
        return userClient.getUser(userId);
    }

    @Override
    public UserResponse updatePassword(long id, UpdateUserPasswordRequest updateRequest) {
        return userClient.updatePassword(id, updateRequest);
    }

    @Override
    public UserResponse updateAvatar(long id, UpdateUserAvatarRequest updateRequest) {
        return userClient.updateAvatar(id, updateRequest.avatar());
    }

    @Override
    public Resource getDocumentSignature(long id) {
        return userClient.getDocumentSignature(id);
    }

    @Override
    public UserResponse updateSignature(long id, UpdateUserDocumentSignatureRequest updateRequest) {
        return userClient.updateSignature(id, updateRequest.signature());
    }
}
