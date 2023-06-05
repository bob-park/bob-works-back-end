package org.bobpark.userservice.domain.team.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.bobpark.core.model.common.Id;
import org.bobpark.userservice.domain.team.entity.Team;
import org.bobpark.userservice.domain.team.model.TeamResponse;
import org.bobpark.userservice.domain.team.service.TeamService;

@RequiredArgsConstructor
@RestController
@RequestMapping("team")
public class TeamController {

    private final TeamService teamService;

    @GetMapping(path = "{teamId:\\d+}")
    public TeamResponse getById(@PathVariable long teamId){
        return teamService.getById(Id.of(Team.class, teamId));
    }
}
