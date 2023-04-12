package org.bobpark.documentservice.domain.position.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.bobpark.documentservice.domain.position.entity.Position;

public interface PositionRepository extends JpaRepository<Position, Long> {
}
