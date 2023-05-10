package org.bobpark.userservice.domain.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.bobpark.core.model.common.Id;
import org.bobpark.userservice.configure.user.properties.UserProperties;
import org.bobpark.userservice.domain.user.entity.User;
import org.bobpark.userservice.domain.user.model.UpdateUserAvatarRequest;
import org.bobpark.userservice.domain.user.model.UserResponse;
import org.bobpark.userservice.domain.user.repository.UserRepository;
import org.bobpark.userservice.domain.user.service.UserAvatarService;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserAvatarServiceImpl implements UserAvatarService {

    private final UserProperties properties;
    
    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserResponse updateAvatar(Id<User, Long> userId, UpdateUserAvatarRequest updateRequest) {
        return null;
    }
}
