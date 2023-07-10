package org.bobpark.userservice.domain.user.repository.query.impl;

import static org.bobpark.userservice.domain.position.entity.QPosition.*;
import static org.bobpark.userservice.domain.user.entity.QUser.*;
import static org.bobpark.userservice.domain.user.entity.QUserPosition.*;
import static org.bobpark.userservice.domain.user.entity.QUserVacation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.bobpark.core.model.common.Id;
import org.bobpark.userservice.domain.position.entity.QPosition;
import org.bobpark.userservice.domain.user.entity.QUserPosition;
import org.bobpark.userservice.domain.user.entity.User;
import org.bobpark.userservice.domain.user.model.SearchUserRequest;
import org.bobpark.userservice.domain.user.repository.query.UserQueryRepository;

@RequiredArgsConstructor
public class UserQueryRepositoryImpl implements UserQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Optional<User> findByUserId(Id<User, String> userId) {
        return Optional.ofNullable(
            query.selectFrom(user)
                .leftJoin(user.vacations, userVacation).fetchJoin()
                .leftJoin(user.position, userPosition).fetchJoin()
                .leftJoin(userPosition.position, position).fetchJoin()
                .where(
                    user.userId.eq(userId.getValue()),
                    userVacation.year.eq(LocalDate.now().getYear()))
                .fetchOne());
    }

    @Override
    public Optional<User> findById(Id<User, Long> id) {
        return Optional.ofNullable(
            query.selectFrom(user)
                .leftJoin(user.vacations, userVacation).fetchJoin()
                .leftJoin(user.position, userPosition).fetchJoin()
                .leftJoin(userPosition.position, position).fetchJoin()
                .where(
                    user.id.eq(id.getValue()),
                    userVacation.year.eq(LocalDate.now().getYear()))
                .fetchOne());
    }

    @Override
    public List<User> getUsersAll() {
        return query.selectFrom(user)
            .leftJoin(user.vacations, userVacation).fetchJoin()
            .leftJoin(user.position, userPosition).fetchJoin()
            .leftJoin(userPosition.position, position).fetchJoin()
            .where(userVacation.year.eq(LocalDate.now().getYear()))
            .fetch();
    }

    @Override
    public List<User> search(SearchUserRequest searchRequest) {
        return query.selectFrom(user)
            .leftJoin(user.vacations, userVacation).fetchJoin()
            .leftJoin(user.position, userPosition).fetchJoin()
            .leftJoin(userPosition.position, position).fetchJoin()
            .where(mappingCondition(searchRequest))
            .fetch();
    }

    private Predicate mappingCondition(SearchUserRequest searchRequest) {

        BooleanBuilder builder = new BooleanBuilder();

        builder.and(inIds(searchRequest.ids()));

        return builder;
    }

    private BooleanExpression inIds(List<Long> ids) {
        return !ids.isEmpty() ? user.id.in(ids) : null;
    }
}
