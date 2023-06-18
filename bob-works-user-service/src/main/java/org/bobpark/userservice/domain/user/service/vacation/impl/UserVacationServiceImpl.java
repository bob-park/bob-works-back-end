package org.bobpark.userservice.domain.user.service.vacation.impl;

import static com.google.common.base.Preconditions.*;
import static org.apache.commons.lang3.ObjectUtils.*;
import static org.bobpark.userservice.domain.user.model.UserResponse.*;

import java.time.LocalDate;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.bobpark.core.exception.NotFoundException;
import org.bobpark.core.exception.ServiceRuntimeException;
import org.bobpark.core.model.common.Id;
import org.bobpark.userservice.configure.user.properties.UserProperties;
import org.bobpark.userservice.domain.user.entity.User;
import org.bobpark.userservice.domain.user.entity.vacation.UserAlternativeVacation;
import org.bobpark.userservice.domain.user.entity.vacation.UserUsedVacation;
import org.bobpark.userservice.domain.user.model.UserResponse;
import org.bobpark.userservice.domain.user.model.vacation.CancelUserVacationRequest;
import org.bobpark.userservice.domain.user.model.vacation.UseAlternativeVacationRequest;
import org.bobpark.userservice.domain.user.model.vacation.UseUserVacationRequest;
import org.bobpark.userservice.domain.user.repository.UserRepository;
import org.bobpark.userservice.domain.user.repository.vacation.UserAlternativeVacationRepository;
import org.bobpark.userservice.domain.user.repository.vacation.UserUsedVacationRepository;
import org.bobpark.userservice.domain.user.service.vacation.UserVacationService;
import org.bobpark.userservice.domain.user.type.VacationType;
import org.bobpark.userservice.exception.user.vacation.OverUsableVacationException;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserVacationServiceImpl implements UserVacationService {

    private final UserProperties properties;

    private final UserRepository userRepository;
    private final UserAlternativeVacationRepository userAlternativeVacationRepository;
    private final UserUsedVacationRepository userUsedVacationRepository;

    @Transactional
    @Override
    public UserResponse useVacation(Id<User, Long> id, UseUserVacationRequest useRequest) {

        checkArgument(isNotEmpty(useRequest.type()), "type must be provided.");

        User user =
            userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        user.useVacation(useRequest.type(), useRequest.useCount());

        if (useRequest.type() == VacationType.ALTERNATIVE) {

            checkArgument(!useRequest.useAlternativeVacationIds().isEmpty(),
                "useAlternativeVacations must be provided.");

            List<UserAlternativeVacation> alternativeVacations =
                userAlternativeVacationRepository.findAllByIds(useRequest.useAlternativeVacationIds());

            double useCount = useRequest.useCount();

            for (UserAlternativeVacation alternativeVacation : alternativeVacations) {

                if (useCount <= 0) {
                    break;
                }

                double usableCount = alternativeVacation.getEffectiveCount() - alternativeVacation.getUsedCount();

                if (usableCount == 0) {
                    continue;
                }

                double usedCount = Math.min(useCount, usableCount);

                alternativeVacation.use(usedCount);

                UserUsedVacation createdUsedVacation =
                    UserUsedVacation.builder()
                        .type(useRequest.type())
                        .usedCount(usedCount)
                        .build();

                createdUsedVacation.setUser(user);
                createdUsedVacation.setAlternativeVacation(alternativeVacation);

                userUsedVacationRepository.save(createdUsedVacation);

                useCount -= usedCount;
            }

            if (useCount != 0) {
                throw new ServiceRuntimeException("Service Error.");
            }

        } else {
            UserUsedVacation createdUsedVacation =
                UserUsedVacation.builder()
                    .type(useRequest.type())
                    .usedCount(useRequest.useCount())
                    .build();

            createdUsedVacation.setUser(user);

            userUsedVacationRepository.save(createdUsedVacation);
        }

        return toResponse(user, properties.getAvatar().getPrefix());
    }

    @Transactional
    @Override
    public UserResponse cancelVacation(Id<User, Long> id, CancelUserVacationRequest cancelRequest) {

        checkArgument(isNotEmpty(cancelRequest.type()), "type must be provided.");

        User user =
            userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        user.cancelVacation(cancelRequest.type(), cancelRequest.cancelCount());

        return toResponse(user, properties.getAvatar().getPrefix());
    }

}
