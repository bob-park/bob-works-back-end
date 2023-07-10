package org.bobpark.userservice.domain.user.repository.vacation.query.impl;

import static org.bobpark.userservice.domain.user.entity.vacation.QUserAlternativeVacation.*;
import static org.bobpark.userservice.domain.user.entity.vacation.QUserUsedVacation.*;

import java.util.List;

import lombok.RequiredArgsConstructor;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.bobpark.userservice.domain.user.entity.vacation.UserUsedVacation;
import org.bobpark.userservice.domain.user.repository.vacation.query.UserUsedVacationQueryRepository;
import org.bobpark.userservice.domain.user.type.VacationType;

@RequiredArgsConstructor
public class UserUsedVacationQueryRepositoryImpl implements UserUsedVacationQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public List<UserUsedVacation> findAllByVacation(Long userId, VacationType type, List<Long> alternativeVacationIds) {
        return query.selectFrom(userUsedVacation)
            .leftJoin(userUsedVacation.alternativeVacation, userAlternativeVacation).fetchJoin()
            .where(
                eqUserId(userId),
                eqType(type),
                eqAlternativeVacationIds(alternativeVacationIds))
            .fetch();
    }

    private BooleanExpression eqUserId(Long userId) {
        return userId != null ? userUsedVacation.user.id.eq(userId) : null;
    }

    private BooleanExpression eqType(VacationType type) {
        return type != null ? userUsedVacation.type.eq(type) : null;
    }

    private BooleanExpression eqAlternativeVacationIds(List<Long> alternativeVacationIds) {
        return alternativeVacationIds.isEmpty() ? null : userAlternativeVacation.id.in(alternativeVacationIds);
    }
}
