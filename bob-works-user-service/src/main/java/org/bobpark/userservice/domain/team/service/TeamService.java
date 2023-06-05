package org.bobpark.userservice.domain.team.service;

import org.bobpark.core.model.common.Id;
import org.bobpark.userservice.domain.team.entity.Team;
import org.bobpark.userservice.domain.team.model.TeamResponse;

public interface TeamService {

    TeamResponse getById(Id<Team, Long> teamId);

}
