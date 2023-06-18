package org.bobpark.userservice.domain.user.service.vacation;

import java.util.List;

import org.bobpark.core.model.common.Id;
import org.bobpark.userservice.domain.user.entity.User;
import org.bobpark.userservice.domain.user.model.UserResponse;
import org.bobpark.userservice.domain.user.model.vacation.AddAlternativeVacationRequest;
import org.bobpark.userservice.domain.user.model.vacation.UserAlternativeVacationResponse;

public interface UserAlternativeVacationService {

    UserResponse addAlternativeVacation(Id<User, Long> id, AddAlternativeVacationRequest addRequest);

    List<UserAlternativeVacationResponse> getUsableAlternativeVacations(Id<User, Long> id);

}
