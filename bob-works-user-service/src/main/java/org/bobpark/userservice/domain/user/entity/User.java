package org.bobpark.userservice.domain.user.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;

import org.bobpark.core.exception.NotFoundException;
import org.bobpark.userservice.common.entity.BaseEntity;
import org.bobpark.userservice.domain.team.entity.Team;
import org.bobpark.userservice.domain.team.entity.TeamUser;
import org.bobpark.userservice.domain.user.type.VacationType;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String name;
    private String email;

    @Exclude
    @OneToOne(mappedBy = "user")
    private UserPosition position;

    @Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<UserVacation> vacations = new ArrayList<>();

    @Exclude
    @OneToOne(mappedBy = "user")
    private TeamUser team;

    @Exclude
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserAvatar avatar;

    @Builder
    private User(Long id, String userId, String name, String email, UserPosition position) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.position = position;
    }

    public void addVacations(UserVacation vacation) {
        vacation.setUser(this);
        getVacations().add(vacation);
    }

    public void cancelVacation(VacationType type, double increaseCount) {

        Vacation vacation = selectVacation(type);

        vacation.cancelVacation(increaseCount);

    }

    public void useVacation(VacationType type, double decreaseCount) {
        Vacation vacation = selectVacation(type);

        vacation.useVacation(decreaseCount);
    }

    public void setTeam(TeamUser teamUser) {
        this.team = teamUser;
    }

    public void setAvatar(String avatarPath) {
        if (getAvatar() == null) {
            this.avatar = new UserAvatar();

            avatar.setUser(this);
        }

        getAvatar().updateAvatarPath(avatarPath);
    }

    private Vacation selectVacation(VacationType type) {

        int nowYear = LocalDate.now().getYear();

        UserVacation userVacation =
            getVacations().stream()
                .filter(vacation -> vacation.getYear() == nowYear)
                .findAny()
                .orElseThrow(() -> new NotFoundException(Vacation.class, type));

        if (type == VacationType.GENERAL) {
            return userVacation.getGeneralVacations();
        }

        return userVacation.getAlternativeVacations();
    }
}
