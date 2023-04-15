package org.bobpark.userservice.domain.user.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
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

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users_vacations")
public class UserVacation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Integer year;

    @Embedded
    @AttributeOverride(name = "totalCount", column = @Column(name = "general_total_count"))
    @AttributeOverride(name = "usedCount", column = @Column(name = "general_used_count"))
    private Vacation generalVacations;

    @Embedded
    @AttributeOverride(name = "totalCount", column = @Column(name = "alternative_total_count"))
    @AttributeOverride(name = "usedCount", column = @Column(name = "alternative_used_count"))
    private Vacation alternativeVacations;

    @Builder
    private UserVacation(Long id, Integer year, Vacation generalVacations, Vacation alternativeVacations) {
        this.id = id;
        this.year = year;
        this.generalVacations = generalVacations;
        this.alternativeVacations = alternativeVacations;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
