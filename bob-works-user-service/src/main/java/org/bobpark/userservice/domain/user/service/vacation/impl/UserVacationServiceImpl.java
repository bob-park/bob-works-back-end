package org.bobpark.userservice.domain.user.service.vacation.impl;

import static com.google.common.base.Preconditions.*;
import static org.apache.commons.lang3.ObjectUtils.*;
import static org.bobpark.userservice.domain.user.model.UserResponse.*;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.bobpark.core.exception.NotFoundException;
import org.bobpark.core.model.common.Id;
import org.bobpark.userservice.configure.user.properties.UserProperties;
import org.bobpark.userservice.domain.user.entity.User;
import org.bobpark.userservice.domain.user.entity.vacation.UserAlternativeVacation;
import org.bobpark.userservice.domain.user.model.UserResponse;
import org.bobpark.userservice.domain.user.model.vacation.CancelUserVacationRequest;
import org.bobpark.userservice.domain.user.model.vacation.UseUserVacationRequest;
import org.bobpark.userservice.domain.user.repository.UserRepository;
import org.bobpark.userservice.domain.user.repository.vacation.UserAlternativeVacationRepository;
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

    @Transactional
    @Override
    public UserResponse useVacation(Id<User, Long> id, UseUserVacationRequest useVacationRequest) {

        checkArgument(isNotEmpty(useVacationRequest.type()), "type must be provided.");

        User user =
            userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        if (useVacationRequest.type() == VacationType.ALTERNATIVE) {

            checkArgument(!useVacationRequest.useAlternativeVacationIds().isEmpty(),
                "useAlternativeVacationIds must be provided.");

            List<UserAlternativeVacation> alternativeVacations =
                userAlternativeVacationRepository.findAllByIds(useVacationRequest.useAlternativeVacationIds());

            double usableTotalCount =
                alternativeVacations.stream()
                    .mapToDouble(item -> item.getEffectiveCount() - item.getUsedCount())
                    .sum();

            if (usableTotalCount < useVacationRequest.useCount()) {
                throw new OverUsableVacationException(usableTotalCount, useVacationRequest.useCount());
            }

            double useCount = useVacationRequest.useCount();

            for (UserAlternativeVacation alternativeVacation : alternativeVacations) {
                double usableCount = alternativeVacation.getEffectiveCount() - alternativeVacation.getUsedCount();

                if (usableCount >= useCount) {
                    // 사용하고자 하는 갯수보다 남아있는 개수가 더 많을 경우, 더 이상 다음 loop 를 타지 않는다.
                    alternativeVacation.use(Math.min(usableCount, useCount));
                    break;
                }

                alternativeVacation.use(usableCount);

                useCount -= usableCount;
            }

        }

        user.useVacation(useVacationRequest.type(), useVacationRequest.useCount());

        return toResponse(user, properties.getAvatar().getPrefix());
    }

    @Transactional
    @Override
    public UserResponse cancelVacation(Id<User, Long> id, CancelUserVacationRequest cancelVacationRequest) {

        checkArgument(isNotEmpty(cancelVacationRequest.type()), "type must be provided.");

        User user =
            userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        user.cancelVacation(cancelVacationRequest.type(), cancelVacationRequest.cancelCount());

        return toResponse(user, properties.getAvatar().getPrefix());
    }

}
