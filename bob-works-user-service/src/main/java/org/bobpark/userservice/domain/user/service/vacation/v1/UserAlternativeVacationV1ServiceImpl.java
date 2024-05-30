package org.bobpark.userservice.domain.user.service.vacation.v1;

import static com.google.common.base.Preconditions.*;
import static org.bobpark.userservice.domain.user.model.UserResponse.*;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.base.Preconditions;

import org.bobpark.core.exception.NotFoundException;
import org.bobpark.core.model.common.Id;
import org.bobpark.userservice.domain.user.entity.User;
import org.bobpark.userservice.domain.user.entity.vacation.UserAlternativeVacation;
import org.bobpark.userservice.domain.user.model.UserResponse;
import org.bobpark.userservice.domain.user.model.vacation.AddAlternativeVacationRequest;
import org.bobpark.userservice.domain.user.model.vacation.UserAlternativeVacationResponse;
import org.bobpark.userservice.domain.user.repository.UserRepository;
import org.bobpark.userservice.domain.user.repository.vacation.UserAlternativeVacationRepository;
import org.bobpark.userservice.domain.user.service.vacation.UserAlternativeVacationService;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserAlternativeVacationV1ServiceImpl implements UserAlternativeVacationService {

    private final UserRepository userRepository;
    private final UserAlternativeVacationRepository userAlternativeVacationRepository;

    @Transactional
    @Override
    public UserResponse addAlternativeVacation(Id<User, Long> id, AddAlternativeVacationRequest addRequest) {

        User user =
            userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        UserAlternativeVacation addAlternativeVacation =
            UserAlternativeVacation.builder()
                .effectiveDate(addRequest.effectiveDate())
                .effectiveCount(addRequest.effectiveCount())
                .effectiveReason(addRequest.effectiveReason())
                .build();

        user.addAlternativeVacation(addAlternativeVacation);

        return toResponse(user);
    }

    @Override
    public List<UserAlternativeVacationResponse> getUsableAlternativeVacations(Id<User, Long> id) {

        List<UserAlternativeVacation> usableAlternativeVacations =
            userAlternativeVacationRepository.findUsableAllByIds(id);

        return usableAlternativeVacations.stream()
            .map(UserAlternativeVacationResponse::toResponse)
            .toList();
    }

    @Override
    public List<UserAlternativeVacationResponse> findAllByIds(List<Long> ids) {

        checkArgument(!CollectionUtils.isEmpty(ids), "ids must be provided.");

        List<UserAlternativeVacation> vacations = userAlternativeVacationRepository.findAllByIds(ids);

        return vacations.stream()
            .map(UserAlternativeVacationResponse::toResponse)
            .toList();
    }

    @Override
    public List<UserAlternativeVacationResponse> getAll(Id<User, Long> userId) {

        List<UserAlternativeVacation> result = userAlternativeVacationRepository.findAllByUser(userId);

        return result.stream()
            .map(UserAlternativeVacationResponse::toResponse)
            .toList();
    }
}
