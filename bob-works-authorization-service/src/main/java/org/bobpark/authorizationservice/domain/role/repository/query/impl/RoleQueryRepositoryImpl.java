package org.bobpark.authorizationservice.domain.role.repository.query.impl;


import static org.bobpark.authorizationservice.domain.role.entity.QRole.*;

import java.util.List;

import lombok.RequiredArgsConstructor;

import com.querydsl.jpa.impl.JPAQueryFactory;

import org.bobpark.authorizationservice.domain.role.entity.QRole;
import org.bobpark.authorizationservice.domain.role.entity.Role;
import org.bobpark.authorizationservice.domain.role.repository.query.RoleQueryRepository;

@RequiredArgsConstructor
public class RoleQueryRepositoryImpl implements RoleQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public List<Role> findAll() {

        QRole parent = new QRole("parent");

        return query.selectFrom(role)
            .leftJoin(role.parent, parent).fetchJoin()
            .fetch();
    }
}
