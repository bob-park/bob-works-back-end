package org.bobpark.documentservice.domain.document.service.holiday;

import java.util.List;

import org.bobpark.core.model.common.Id;
import org.bobpark.documentservice.domain.document.entity.holiday.HolidayWorkTime;
import org.bobpark.documentservice.domain.document.model.holiday.HolidayWorkTimeLogResponse;

public interface HolidayWorkTimeLogService {

    List<HolidayWorkTimeLogResponse> getLog(Id<HolidayWorkTime, Long> workTimeId);

}
