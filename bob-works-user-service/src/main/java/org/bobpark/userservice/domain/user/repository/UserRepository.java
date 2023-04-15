package org.bobpark.userservice.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.bobpark.userservice.domain.user.entity.User;
import org.bobpark.userservice.domain.user.repository.query.UserQueryRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserQueryRepository {

}
