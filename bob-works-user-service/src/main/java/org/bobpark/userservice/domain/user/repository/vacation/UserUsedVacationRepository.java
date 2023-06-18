package org.bobpark.userservice.domain.user.repository.vacation;

import org.springframework.data.jpa.repository.JpaRepository;

import org.bobpark.userservice.domain.user.entity.vacation.UserUsedVacation;

public interface UserUsedVacationRepository extends JpaRepository<UserUsedVacation, Long> {
}
