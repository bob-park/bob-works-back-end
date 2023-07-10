package org.bobpark.userservice.domain.user.repository.vacation.query;

import java.util.List;

import org.bobpark.userservice.domain.user.entity.vacation.UserUsedVacation;
import org.bobpark.userservice.domain.user.type.VacationType;

public interface UserUsedVacationQueryRepository {

    List<UserUsedVacation> findAllByVacation(Long userId, VacationType type, List<Long> alternativeVacationIds);

}
