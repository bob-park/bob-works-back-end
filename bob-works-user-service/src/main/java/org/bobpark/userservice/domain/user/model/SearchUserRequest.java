package org.bobpark.userservice.domain.user.model;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

import java.util.Collections;
import java.util.List;

public record SearchUserRequest(List<Long> ids) {

    public SearchUserRequest {
        if (isEmpty(ids)) {
            ids = Collections.emptyList();
        }
    }

}
