package org.bobpark.userservice.domain.user.repository.vacation.query;

import java.util.List;

import org.bobpark.core.model.common.Id;
import org.bobpark.userservice.domain.user.entity.User;
import org.bobpark.userservice.domain.user.entity.vacation.UserAlternativeVacation;

public interface UserAlternativeVacationQueryRepository {

    List<UserAlternativeVacation> findUsableAllByIds(Id<User, Long> id);

    List<UserAlternativeVacation> findAllByIds(List<Long> ids);

}
