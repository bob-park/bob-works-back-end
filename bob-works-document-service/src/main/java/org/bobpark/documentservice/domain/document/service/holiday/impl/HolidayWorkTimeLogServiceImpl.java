package org.bobpark.documentservice.domain.document.service.holiday.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.bobpark.core.model.common.Id;
import org.bobpark.documentservice.domain.document.entity.holiday.HolidayWorkTime;
import org.bobpark.documentservice.domain.document.entity.holiday.HolidayWorkTimeLog;
import org.bobpark.documentservice.domain.document.model.holiday.HolidayWorkTimeLogResponse;
import org.bobpark.documentservice.domain.document.repository.holiday.HolidayWorkTimeLogRepository;
import org.bobpark.documentservice.domain.document.service.holiday.HolidayWorkTimeLogService;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class HolidayWorkTimeLogServiceImpl implements HolidayWorkTimeLogService {

    private final HolidayWorkTimeLogRepository logRepository;

    @Override
    public List<HolidayWorkTimeLogResponse> getLog(Id<HolidayWorkTime, Long> workTimeId) {

        List<HolidayWorkTimeLog> result = logRepository.getLogByWorkTimeId(workTimeId);

        return result.stream()
            .map(item ->
                HolidayWorkTimeLogResponse.builder()
                    .id(item.getId())
                    .calculationLog(item.getCalculationLog())
                    .build())
            .toList();
    }
}
