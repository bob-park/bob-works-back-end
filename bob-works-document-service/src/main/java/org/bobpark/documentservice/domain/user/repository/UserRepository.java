package org.bobpark.documentservice.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.bobpark.documentservice.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
