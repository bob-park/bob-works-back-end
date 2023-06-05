package org.bobpark.userservice.domain.team.service.impl;

import static org.bobpark.userservice.domain.team.model.TeamResponse.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.bobpark.core.exception.NotFoundException;
import org.bobpark.core.model.common.Id;
import org.bobpark.userservice.domain.team.entity.Team;
import org.bobpark.userservice.domain.team.model.TeamResponse;
import org.bobpark.userservice.domain.team.repository.TeamRepository;
import org.bobpark.userservice.domain.team.service.TeamService;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    @Override
    public TeamResponse getById(Id<Team, Long> teamId) {

        Team team =
            teamRepository.findById(teamId.getValue())
                .orElseThrow(() -> new NotFoundException(teamId));

        return toResponse(team);
    }

}
