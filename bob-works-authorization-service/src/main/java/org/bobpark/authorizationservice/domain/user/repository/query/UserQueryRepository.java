package org.bobpark.authorizationservice.domain.user.repository.query;

import java.util.Optional;

import org.bobpark.authorizationservice.domain.user.entity.User;

public interface UserQueryRepository {
    Optional<User> findByUserId(String userId);
}
