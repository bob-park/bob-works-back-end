package org.bobpark.userservice.domain.user.repository.vacation;

import org.springframework.data.jpa.repository.JpaRepository;

import org.bobpark.userservice.domain.user.entity.vacation.UserAlternativeVacation;
import org.bobpark.userservice.domain.user.repository.vacation.query.UserAlternativeVacationQueryRepository;

public interface UserAlternativeVacationRepository extends JpaRepository<UserAlternativeVacation, Long>,
    UserAlternativeVacationQueryRepository {
}
