package org.bobpark.userservice.domain.user.repository.vacation.query;

import java.util.List;

import org.bobpark.userservice.domain.user.entity.vacation.UserAlternativeVacation;

public interface UserAlternativeVacationQueryRepository {

    List<UserAlternativeVacation> findUsableAllByIds();

    List<UserAlternativeVacation> findAllByIds(List<Long> ids);

}
