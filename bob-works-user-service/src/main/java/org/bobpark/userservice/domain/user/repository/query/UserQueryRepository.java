package org.bobpark.userservice.domain.user.repository.query;

import java.util.List;
import java.util.Optional;

import org.bobpark.core.model.common.Id;
import org.bobpark.userservice.domain.user.entity.User;
import org.bobpark.userservice.domain.user.model.SearchUserRequest;

public interface UserQueryRepository {

    Optional<User> findByUserId(Id<User, String> userId);

    Optional<User> findById(Id<User, Long> id);

    List<User> getUsersAll();

    List<User> search(SearchUserRequest searchRequest);

    boolean existUserId(String userId);

}
