package org.bobpark.userservice.domain.user.service.vacation;

import org.bobpark.core.model.common.Id;
import org.bobpark.userservice.domain.user.entity.User;
import org.bobpark.userservice.domain.user.model.UserResponse;
import org.bobpark.userservice.domain.user.model.vacation.AddAlternativeVacationRequest;

public interface UserAlternativeVacationService {

    UserResponse addAlternativeVacation(Id<User, Long> id, AddAlternativeVacationRequest addRequest);
}
