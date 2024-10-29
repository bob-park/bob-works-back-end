package org.bobpark.documentservice.domain.document.repository.holiday.query.impl;

import static org.bobpark.documentservice.domain.document.entity.holiday.QHolidayWorkTimeLog.*;

import java.util.List;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

import com.querydsl.jpa.impl.JPAQueryFactory;

import org.bobpark.core.model.common.Id;
import org.bobpark.documentservice.domain.document.entity.holiday.HolidayWorkTime;
import org.bobpark.documentservice.domain.document.entity.holiday.HolidayWorkTimeLog;
import org.bobpark.documentservice.domain.document.entity.holiday.QHolidayWorkTimeLog;
import org.bobpark.documentservice.domain.document.repository.holiday.query.HolidayWorkTimeLogQueryRepository;

@RequiredArgsConstructor
public class HolidayWorkTimeLogQueryRepositoryImpl implements HolidayWorkTimeLogQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public List<HolidayWorkTimeLog> getLogByWorkTimeId(Id<HolidayWorkTime, Long> workTimeId) {
        return query.selectFrom(holidayWorkTimeLog)
            .where(holidayWorkTimeLog.workTime.id.eq(workTimeId.getValue()))
            .orderBy(holidayWorkTimeLog.createdDate.asc())
            .fetch();
    }
}
