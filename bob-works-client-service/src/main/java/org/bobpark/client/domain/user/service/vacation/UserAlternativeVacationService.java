package org.bobpark.client.domain.user.service.vacation;

import java.util.List;

import org.bobpark.client.domain.user.model.vacation.UserAlternativeVacationResponse;

public interface UserAlternativeVacationService {
    List<UserAlternativeVacationResponse> getUsableList(long id);

}
