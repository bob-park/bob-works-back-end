package org.bobpark.userservice.domain.user.repository.query;

import java.util.Optional;

import org.bobpark.core.model.common.Id;
import org.bobpark.userservice.domain.user.entity.User;

public interface UserQueryRepository {

    Optional<User> findByUserId(Id<User, String> userId);

}
