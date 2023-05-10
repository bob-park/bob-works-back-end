package org.bobpark.userservice.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.bobpark.userservice.domain.user.entity.UserAvatar;

public interface UserAvatarRepository extends JpaRepository<UserAvatar, Long> {
}
