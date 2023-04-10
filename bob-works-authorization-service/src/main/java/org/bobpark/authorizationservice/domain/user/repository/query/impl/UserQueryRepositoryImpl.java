package org.bobpark.authorizationservice.domain.user.repository.query.impl;

import static org.bobpark.authorizationservice.domain.role.entity.QRole.*;
import static org.bobpark.authorizationservice.domain.user.entity.QUser.*;
import static org.bobpark.authorizationservice.domain.user.entity.QUserRole.*;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

import com.querydsl.jpa.impl.JPAQueryFactory;

import org.bobpark.authorizationservice.domain.user.entity.User;
import org.bobpark.authorizationservice.domain.user.repository.query.UserQueryRepository;

@RequiredArgsConstructor
public class UserQueryRepositoryImpl implements UserQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Optional<User> findByUserId(String userId) {
        return Optional.ofNullable(
            query.selectFrom(user)
                .leftJoin(user.roles, userRole).fetchJoin()
                .leftJoin(userRole.role, role).fetchJoin()
                .where(user.userId.eq(userId))
                .distinct()
                .fetchOne());
    }
}
