package org.bobpark.client.common.utils;

import static org.apache.commons.lang3.math.NumberUtils.*;

import java.util.Map;

import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.bobpark.client.domain.position.model.PositionResponse;
import org.bobpark.client.domain.team.model.TeamResponse;
import org.bobpark.client.domain.user.model.UserResponse;
import org.bobpark.client.domain.user.model.vacation.UserVacationResponse;
import org.bobpark.core.exception.ServiceRuntimeException;

public interface CommonUtils {

    ObjectMapper mapper = new ObjectMapper();

    static UserResponse parseToUserResponse(OidcUser user){
        Map<String, Object> profile = user.getUserInfo().getClaim("profile");

        Map<String, Object> positionMap = (Map<String, Object>)profile.get("position");
        Map<String, Object> teamMap = (Map<String, Object>)profile.get("team");

        UserVacationResponse userVacationResponse = null;

        try {
            String nowVacationStr = mapper.writeValueAsString(profile.get("nowVacation"));

            userVacationResponse = mapper.readValue(nowVacationStr, UserVacationResponse.class);

        } catch (JsonProcessingException e) {
            throw new ServiceRuntimeException(e);
        }

        return UserResponse.builder()
            .id(toLong(String.valueOf(profile.get("id"))))
            .email(user.getUserInfo().getEmail())
            .name((String)profile.get("name"))
            .userId(user.getClaimAsString("sub"))
            .avatar((String)profile.get("avatar"))
            .nowVacation(userVacationResponse)
            .position(
                PositionResponse.builder()
                    .id(toLong(String.valueOf(positionMap.get("id"))))
                    .name(String.valueOf(positionMap.get("name")))
                    .build())
            .team(teamMap != null ?
                TeamResponse.builder()
                    .id(toLong(String.valueOf(teamMap.get("id"))))
                    .name(String.valueOf(teamMap.get("name")))
                    .description(String.valueOf(teamMap.get("description")))
                    .build() : null)
            .build();
    }
}
