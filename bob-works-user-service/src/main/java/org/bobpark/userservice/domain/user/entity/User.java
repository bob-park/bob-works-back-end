package org.bobpark.userservice.domain.user.entity;

import static com.google.common.base.Preconditions.*;
import static org.apache.commons.lang3.ObjectUtils.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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

import org.apache.commons.lang3.StringUtils;

import org.bobpark.core.exception.NotFoundException;
import org.bobpark.userservice.common.entity.BaseEntity;
import org.bobpark.userservice.domain.position.entity.Position;
import org.bobpark.userservice.domain.team.entity.TeamUser;
import org.bobpark.userservice.domain.user.entity.vacation.UserAlternativeVacation;
import org.bobpark.userservice.domain.user.entity.vacation.UserUsedVacation;
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

    @Column(name = "password")
    private String encryptPassword;

    private String name;
    private String email;

    private LocalDate employmentDate;

    @Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRole> roles = new ArrayList<>();

    @Exclude
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
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

    @Exclude
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserDocumentSignature signature;

    @Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserAlternativeVacation> alternativeVacations = new ArrayList<>();

    @Exclude
    @OneToMany(mappedBy = "user")
    private List<UserUsedVacation> usedVacations = new ArrayList<>();

    @Builder
    private User(Long id, String userId, String encryptPassword, String name, String email, Long roleId,
        UserPosition position, LocalDate employmentDate) {

        checkArgument(StringUtils.isNotBlank(userId), "userId must be provided.");
        checkArgument(StringUtils.isNotBlank(encryptPassword), "encryptPassword must be provided.");
        checkArgument(StringUtils.isNotBlank(name), "name must be provided.");
        checkArgument(StringUtils.isNotBlank(email), "email must be provided.");
        checkArgument(isNotEmpty(employmentDate), "employmentDate must be provided.");

        this.id = id;
        this.userId = userId;
        this.encryptPassword = encryptPassword;
        this.name = name;
        this.email = email;
        this.employmentDate = employmentDate;
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

    public void updatePassword(String encryptPassword) {

        checkArgument(StringUtils.isNotBlank(encryptPassword), "password must be provided.");

        this.encryptPassword = encryptPassword;
    }

    public void updateSignature(String signaturePath) {

        UserDocumentSignature updateSignature =
            UserDocumentSignature.builder()
                .signaturePath(signaturePath)
                .build();

        updateSignature.setUser(this);

        signature = updateSignature;
    }

    public void addAlternativeVacation(UserAlternativeVacation alternativeVacation) {
        alternativeVacation.setUser(this);

        getAlternativeVacations().add(alternativeVacation);

        // 대체 휴가 생성 갯수 만큼 증가
        Vacation nowAlternativeVacation = selectVacation(VacationType.ALTERNATIVE);

        nowAlternativeVacation.addTotalCount(alternativeVacation.getEffectiveCount());
    }

    public void setPosition(Position position) {

        UserPosition userPosition = new UserPosition();

        userPosition.setUser(this);
        userPosition.setPosition(position);

        this.position = userPosition;
    }

    public void addRole(long roleId) {

        UserRole createUserRole =
            UserRole.builder()
                .roleId(roleId)
                .build();

        createUserRole.setUser(this);

        getRoles().add(createUserRole);
    }

    public void createVacations(int year, double generalTotalCount, double alternativeTotalCount) {

        UserVacation createVacation =
            UserVacation.builder()
                .year(year)
                .generalVacations(
                    Vacation.builder()
                        .totalCount(generalTotalCount)
                        .build())
                .alternativeVacations(
                    Vacation.builder()
                        .totalCount(alternativeTotalCount)
                        .build())
                .build();

        createVacation.setUser(this);

        getVacations().add(createVacation);

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
