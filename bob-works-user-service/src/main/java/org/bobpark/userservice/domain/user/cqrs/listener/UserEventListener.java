package org.bobpark.userservice.domain.user.cqrs.listener;

import java.time.LocalDate;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import org.bobpark.core.exception.NotFoundException;
import org.bobpark.userservice.domain.position.entity.Position;
import org.bobpark.userservice.domain.position.repository.PositionRepository;
import org.bobpark.userservice.domain.role.feign.client.RoleClient;
import org.bobpark.userservice.domain.role.model.RoleResponse;
import org.bobpark.userservice.domain.team.entity.Team;
import org.bobpark.userservice.domain.team.repository.TeamRepository;
import org.bobpark.userservice.domain.user.cqrs.event.CreatedUserEvent;
import org.bobpark.userservice.domain.user.entity.User;
import org.bobpark.userservice.domain.user.repository.UserRepository;

@Slf4j
@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
public class UserEventListener {

    private final RoleClient roleClient;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final PositionRepository positionRepository;

    @Transactional
    @EventListener
    public void handleCreatedUser(CreatedUserEvent createdEvent) {

        Team team =
            teamRepository.findById(createdEvent.teamId())
                .orElseThrow(() -> new NotFoundException(Team.class, createdEvent.teamId()));

        Position position =
            positionRepository.findById(createdEvent.positionId())
                .orElseThrow(() -> new NotFoundException(Position.class, createdEvent.positionId()));

        RoleResponse role = getRole(createdEvent.roleId());

        User createUser =
            User.builder()
                .userId(createdEvent.userId())
                .encryptPassword(createdEvent.encryptPassword())
                .name(createdEvent.name())
                .email(createdEvent.email())
                .employmentDate(createdEvent.employmentDate())
                .build();

        // add role
        createUser.addRole(role.id());

        // set position
        createUser.setPosition(position);

        // set vacation
        createUser.createVacations(
            LocalDate.now().getYear(),
            createdEvent.vacation().generalTotalCount(),
            createdEvent.vacation().alternativeTotalCount());

        // add team
        team.addUser(createUser);

        userRepository.save(createUser);

        log.debug("created user. (userId={})", createdEvent.userId());

    }

    private RoleResponse getRole(long roleId) {
        List<RoleResponse> roles = roleClient.getRoles();

        return roles.stream()
            .filter(role -> role.id() == roleId)
            .findAny()
            .orElseThrow(() -> new NotFoundException(RoleResponse.class, roleId));
    }

}
