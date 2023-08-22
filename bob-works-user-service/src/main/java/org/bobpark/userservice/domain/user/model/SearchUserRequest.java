package org.bobpark.userservice.domain.user.model;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

import java.util.Collections;
import java.util.List;

import lombok.Builder;

@Builder
public record SearchUserRequest(List<Long> ids, Integer vacationYear) {

    public SearchUserRequest {
        if (isEmpty(ids)) {
            ids = Collections.emptyList();
        }
    }

}
