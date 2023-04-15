package org.bobpark.userservice.domain.user.service.vacation.impl;

import static com.google.common.base.Preconditions.*;
import static org.apache.commons.lang3.ObjectUtils.*;
import static org.bobpark.userservice.domain.user.model.UserResponse.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.bobpark.core.exception.NotFoundException;
import org.bobpark.core.model.common.Id;
import org.bobpark.userservice.domain.user.entity.User;
import org.bobpark.userservice.domain.user.model.UserResponse;
import org.bobpark.userservice.domain.user.model.vacation.CancelUserVacationRequest;
import org.bobpark.userservice.domain.user.model.vacation.UseUserVacationRequest;
import org.bobpark.userservice.domain.user.repository.UserRepository;
import org.bobpark.userservice.domain.user.service.vacation.UserVacationService;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserVacationServiceImpl implements UserVacationService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserResponse useVacation(Id<User, Long> id, UseUserVacationRequest useVacationRequest) {

        checkArgument(isNotEmpty(useVacationRequest.type()), "type must be provided.");

        User user =
            userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        user.useVacation(useVacationRequest.type(), useVacationRequest.useCount());

        return toResponse(user);
    }

    @Transactional
    @Override
    public UserResponse cancelVacation(Id<User, Long> id, CancelUserVacationRequest cancelVacationRequest) {

        checkArgument(isNotEmpty(cancelVacationRequest.type()), "type must be provided.");

        User user =
            userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        user.cancelVacation(cancelVacationRequest.type(), cancelVacationRequest.cancelCount());

        return toResponse(user);
    }
}
