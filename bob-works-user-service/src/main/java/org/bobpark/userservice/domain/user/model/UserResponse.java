package org.bobpark.userservice.domain.user.model;

import java.time.LocalDate;

import lombok.Builder;

import org.apache.commons.lang.StringUtils;

import org.bobpark.userservice.domain.position.model.PositionResponse;
import org.bobpark.userservice.domain.team.model.TeamResponse;
import org.bobpark.userservice.domain.user.entity.User;
import org.bobpark.userservice.domain.user.entity.UserPosition;
import org.bobpark.userservice.domain.user.model.vacation.UserVacationResponse;

@Builder
public record UserResponse(Long id,
                           String userId,
                           String email,
                           String name,
                           String avatar,
                           PositionResponse position,
                           UserVacationResponse nowVacation,
                           TeamResponse team) {

    public static UserResponse toResponse(User user) {
        return toResponse(user, null);
    }

    public static UserResponse toResponse(User user, String prefixAvatar) {

        UserPosition position = user.getPosition();
        PositionResponse positionResponse = null;

        if (position != null) {
            positionResponse =
                PositionResponse.builder()
                    .id(position.getPosition().getId())
                    .name(position.getPosition().getName())
                    .build();
        }

        UserVacationResponse userVacation =
            user.getVacations().stream()
                .filter(item -> item.getYear() == LocalDate.now().getYear())
                .map(UserVacationResponse::toResponse)
                .findAny()
                .orElse(null);

        String userAvatar = null;

        if (StringUtils.isNotBlank(prefixAvatar) && user.getAvatar() != null) {
            userAvatar = prefixAvatar + "/" + user.getAvatar().getId();
        }

        return UserResponse.builder()
            .id(user.getId())
            .userId(user.getUserId())
            .email(user.getEmail())
            .name(user.getName())
            .avatar(userAvatar)
            .position(positionResponse)
            .nowVacation(userVacation)
            .team(user.getTeam() != null ? TeamResponse.toResponse(user.getTeam().getTeam()) : null)
            .build();
    }

}
