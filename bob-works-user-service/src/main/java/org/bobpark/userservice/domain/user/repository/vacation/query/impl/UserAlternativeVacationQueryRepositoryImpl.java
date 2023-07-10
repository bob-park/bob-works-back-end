package org.bobpark.userservice.domain.user.repository.vacation.query.impl;

import static org.bobpark.userservice.domain.user.entity.QUser.*;
import static org.bobpark.userservice.domain.user.entity.vacation.QUserAlternativeVacation.*;

import java.util.List;

import lombok.RequiredArgsConstructor;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.bobpark.core.model.common.Id;
import org.bobpark.userservice.domain.user.entity.User;
import org.bobpark.userservice.domain.user.entity.vacation.UserAlternativeVacation;
import org.bobpark.userservice.domain.user.repository.vacation.query.UserAlternativeVacationQueryRepository;

@RequiredArgsConstructor
public class UserAlternativeVacationQueryRepositoryImpl implements UserAlternativeVacationQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public List<UserAlternativeVacation> findUsableAllByIds(Id<User, Long> userId) {
        return query.selectFrom(userAlternativeVacation)
            .join(userAlternativeVacation.user, user).fetchJoin()
            .where(
                eqUserId(userId),
                userAlternativeVacation.isExpired.eq(false),
                userAlternativeVacation.effectiveCount.gt(userAlternativeVacation.usedCount))
            .orderBy(userAlternativeVacation.effectiveDate.asc())
            .fetch();
    }

    @Override
    public List<UserAlternativeVacation> findAllByIds(List<Long> ids) {
        return query.selectFrom(userAlternativeVacation)
            .join(userAlternativeVacation.user, user).fetchJoin()
            .where(
                userAlternativeVacation.isExpired.eq(false),
                eqIds(ids))
            .orderBy(userAlternativeVacation.effectiveDate.asc())
            .fetch();
    }

    private BooleanExpression eqUserId(Id<User, Long> userId) {
        return userId != null ? user.id.eq(userId.getValue()) : null;
    }

    private BooleanExpression eqIds(List<Long> ids) {
        return ids.isEmpty() ? null : userAlternativeVacation.id.in(ids);
    }
}
