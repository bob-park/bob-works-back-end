package org.bobpark.noticeservice.domain.user.model;

public record UserResponse(Long id,
                           String userId,
                           String email,
                           String name) {
}
