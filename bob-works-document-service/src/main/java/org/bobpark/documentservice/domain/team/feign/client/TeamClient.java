package org.bobpark.documentservice.domain.team.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.bobpark.documentservice.domain.team.model.TeamResponse;

@FeignClient(name = "user-service", contextId = "team-service")
public interface TeamClient {

    @GetMapping(path = "team/{teamId:\\d+}")
    TeamResponse getById(@PathVariable long teamId);

}
