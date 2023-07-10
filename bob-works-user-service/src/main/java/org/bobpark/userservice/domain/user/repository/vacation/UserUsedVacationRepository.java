package org.bobpark.userservice.domain.user.repository.vacation;

import org.springframework.data.jpa.repository.JpaRepository;

import org.bobpark.userservice.domain.user.entity.vacation.UserUsedVacation;
import org.bobpark.userservice.domain.user.repository.vacation.query.UserUsedVacationQueryRepository;

public interface UserUsedVacationRepository extends JpaRepository<UserUsedVacation, Long>,
    UserUsedVacationQueryRepository {
}
