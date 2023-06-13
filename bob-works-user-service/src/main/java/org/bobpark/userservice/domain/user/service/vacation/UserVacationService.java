package org.bobpark.userservice.domain.user.service.vacation;

import org.bobpark.core.model.common.Id;
import org.bobpark.userservice.domain.user.entity.User;
import org.bobpark.userservice.domain.user.model.vacation.AddTotalAlternativeVacationRequest;
import org.bobpark.userservice.domain.user.model.vacation.CancelUserVacationRequest;
import org.bobpark.userservice.domain.user.model.vacation.UseUserVacationRequest;
import org.bobpark.userservice.domain.user.model.UserResponse;

public interface UserVacationService {

    UserResponse useVacation(Id<User, Long> id, UseUserVacationRequest useVacationRequest);
    UserResponse cancelVacation(Id<User, Long> id, CancelUserVacationRequest cancelVacationRequest);

    UserResponse addTotalAlternativeVacation(Id<User, Long> id, AddTotalAlternativeVacationRequest addRequest);

}
