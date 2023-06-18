package org.bobpark.userservice.domain.user.entity.vacation;

import static com.google.common.base.Preconditions.*;
import static org.apache.commons.lang3.ObjectUtils.*;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;

import org.bobpark.userservice.common.entity.BaseEntity;
import org.bobpark.userservice.domain.user.entity.User;
import org.bobpark.userservice.domain.user.type.VacationType;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users_used_vacations")
public class UserUsedVacation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private VacationType type;

    @Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alternative_vacation_id")
    private UserAlternativeVacation alternativeVacation;

    private Double usedCount;

    @Builder
    private UserUsedVacation(Long id, VacationType type, Double usedCount) {

        checkArgument(isNotEmpty(type), "type must be provided.");
        checkArgument(isNotEmpty(usedCount), "usedCount must be provided.");

        this.id = id;
        this.type = type;
        this.usedCount = usedCount;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAlternativeVacation(UserAlternativeVacation alternativeVacation) {
        this.alternativeVacation = alternativeVacation;
    }
}
