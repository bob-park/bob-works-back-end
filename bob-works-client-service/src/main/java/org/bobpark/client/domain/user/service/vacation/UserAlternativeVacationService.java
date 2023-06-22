package org.bobpark.client.domain.user.service.vacation;

import java.util.List;

import org.bobpark.client.domain.user.model.vacation.UserAlternativeVacation;

public interface UserAlternativeVacationService {
    List<UserAlternativeVacation> getUsableList(long id);

}
