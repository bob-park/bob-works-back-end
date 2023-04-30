package org.bobpark.userservice.domain.team.entity;

import static com.google.common.base.Preconditions.*;
import static org.springframework.util.StringUtils.*;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;

import org.bobpark.userservice.common.entity.BaseEntity;
import org.bobpark.userservice.domain.user.entity.User;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "teams")
public class Team extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "team")
    private List<TeamUser> users = new ArrayList<>();

    @Builder
    private Team(Long id, String name, String description) {

        checkArgument(hasText(name), "name must be provided.");
        checkArgument(hasText(description), "description must be provided.");

        this.id = id;
        this.name = name;
        this.description = description;
    }

    public void addUser(User user) {
        if (users.stream().anyMatch(item -> item.getUser() == user)) {
            return;
        }

        TeamUser teamUser = new TeamUser();
        teamUser.setTeam(this);
        teamUser.setUser(user);

        user.setTeam(teamUser);

        users.add(teamUser);
    }
}
