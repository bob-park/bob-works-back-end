package org.bobpark.userservice.domain.position.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.bobpark.userservice.domain.position.entity.Position;

public interface PositionRepository extends JpaRepository<Position, Long> {
}
