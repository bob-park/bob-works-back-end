package org.bobpark.documentservice.domain.document.controller.holiday;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.bobpark.core.model.common.Id;
import org.bobpark.documentservice.domain.document.entity.holiday.HolidayWorkTime;
import org.bobpark.documentservice.domain.document.model.holiday.HolidayWorkTimeLogResponse;
import org.bobpark.documentservice.domain.document.service.holiday.HolidayWorkTimeLogService;

@RequiredArgsConstructor
@RestController
@RequestMapping("document/holiday/work/time")
public class HolidayWorkTimeController {

    private final HolidayWorkTimeLogService logService;

    @GetMapping(path = "{workTimeId:\\d+}/logs")
    public List<HolidayWorkTimeLogResponse> getLogs(@PathVariable long workTimeId) {
        return logService.getLog(Id.of(HolidayWorkTime.class, workTimeId));
    }

}
