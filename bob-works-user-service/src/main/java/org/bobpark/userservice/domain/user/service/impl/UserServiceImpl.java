package org.bobpark.userservice.domain.user.service.impl;

import static com.google.common.base.Preconditions.*;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.apache.commons.lang3.StringUtils;

import org.bobpark.core.exception.NotFoundException;
import org.bobpark.core.model.common.Id;
import org.bobpark.userservice.configure.user.properties.UserProperties;
import org.bobpark.userservice.domain.user.entity.User;
import org.bobpark.userservice.domain.user.model.UpdateUserPasswordRequest;
import org.bobpark.userservice.domain.user.model.UserResponse;
import org.bobpark.userservice.domain.user.repository.UserRepository;
import org.bobpark.userservice.domain.user.service.UserService;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private static final String DEFAULT_PASSWORD_ENCODING_ID = "bcrypt";

    private final UserProperties properties;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse getUser(Id<User, String> userId) {

        User user = userRepository.findByUserId(userId)
            .orElseThrow(() -> new NotFoundException(userId));

        return toResponse(user);
    }

    @Transactional
    @Override
    public UserResponse updatePassword(Id<User, Long> userId, UpdateUserPasswordRequest updateRequest) {

        checkArgument(StringUtils.isNotBlank(updateRequest.password()), "password must be provided.");

        User user =
            userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(userId));

        user.updatePassword(passwordEncoder.encode(updateRequest.password()));

        return toResponse(user);
    }

    @Override
    public List<UserResponse> getUsersAll() {
        return userRepository.getUsersAll().stream()
            .map(item -> UserResponse.toResponse(item, properties.getAvatar().getPrefix()))
            .toList();
    }

    private UserResponse toResponse(User user){
        return UserResponse.toResponse(user, properties.getAvatar().getPrefix());
    }

}
