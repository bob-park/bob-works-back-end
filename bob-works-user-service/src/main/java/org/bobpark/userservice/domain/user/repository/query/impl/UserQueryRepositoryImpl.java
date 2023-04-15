package org.bobpark.userservice.domain.user.repository.query.impl;

import static org.bobpark.userservice.domain.user.entity.QUser.*;
import static org.bobpark.userservice.domain.user.entity.QUserVacation.*;

import java.time.LocalDate;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

import com.querydsl.jpa.impl.JPAQueryFactory;

import org.bobpark.core.model.common.Id;
import org.bobpark.userservice.domain.user.entity.User;
import org.bobpark.userservice.domain.user.repository.query.UserQueryRepository;

@RequiredArgsConstructor
public class UserQueryRepositoryImpl implements UserQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Optional<User> findByUserId(Id<User, String> userId) {
        return Optional.ofNullable(
            query.selectFrom(user)
                .leftJoin(user.vacations, userVacation).fetchJoin()
                .where(
                    user.userId.eq(userId.getValue()),
                    userVacation.year.eq(LocalDate.now().getYear()))
                .fetchOne());
    }
}
