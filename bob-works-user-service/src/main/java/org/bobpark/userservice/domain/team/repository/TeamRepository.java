package org.bobpark.userservice.domain.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.bobpark.userservice.domain.team.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
