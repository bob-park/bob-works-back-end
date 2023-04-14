package org.bobpark.documentservice.domain.user.utils;

import java.util.List;

import org.bobpark.core.exception.NotFoundException;
import org.bobpark.documentservice.domain.user.model.UserResponse;

public interface UserUtils {

    static UserResponse findByUser(List<UserResponse> users, Long id) {
        return users.stream()
            .filter(user -> user.id().equals(id))
            .findAny()
            .orElseThrow(() -> new NotFoundException(UserResponse.class, id));
    }
}
