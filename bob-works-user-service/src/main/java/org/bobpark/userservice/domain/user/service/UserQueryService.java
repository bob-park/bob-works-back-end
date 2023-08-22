package org.bobpark.userservice.domain.user.service;

import static com.google.common.base.Preconditions.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.apache.commons.lang.StringUtils;

import org.bobpark.userservice.domain.user.model.CheckExistUserResponse;
import org.bobpark.userservice.domain.user.repository.UserRepository;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserQueryService {

    private final UserRepository userRepository;

    public CheckExistUserResponse existUserId(String userId) {

        checkArgument(StringUtils.isNotBlank(userId), "userId must be provided.");

        boolean exist = userRepository.existUserId(userId);

        return new CheckExistUserResponse(exist);
    }
}
