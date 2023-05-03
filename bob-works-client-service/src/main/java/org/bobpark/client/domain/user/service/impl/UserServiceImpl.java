package org.bobpark.client.domain.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import org.bobpark.client.domain.user.feign.UserClient;
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
}
