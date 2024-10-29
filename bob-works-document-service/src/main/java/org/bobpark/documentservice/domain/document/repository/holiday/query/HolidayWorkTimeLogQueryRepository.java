package org.bobpark.documentservice.domain.document.repository.holiday.query;

import java.util.List;

import org.bobpark.core.model.common.Id;
import org.bobpark.documentservice.domain.document.entity.holiday.HolidayWorkTime;
import org.bobpark.documentservice.domain.document.entity.holiday.HolidayWorkTimeLog;

public interface HolidayWorkTimeLogQueryRepository {

    List<HolidayWorkTimeLog> getLogByWorkTimeId(Id<HolidayWorkTime, Long> workTimeId);

}
