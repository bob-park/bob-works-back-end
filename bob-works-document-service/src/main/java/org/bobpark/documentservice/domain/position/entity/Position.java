package org.bobpark.documentservice.domain.position.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;

import org.bobpark.documentservice.common.entity.BaseEntity;
import org.bobpark.documentservice.domain.user.entity.User;
import org.bobpark.documentservice.domain.user.entity.UserPosition;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "positions")
public class Position extends BaseEntity {

    @Id
    private Long id;

    private String name;


    @Exclude
    @OneToOne(mappedBy = "position")
    private UserPosition userPosition;
}
