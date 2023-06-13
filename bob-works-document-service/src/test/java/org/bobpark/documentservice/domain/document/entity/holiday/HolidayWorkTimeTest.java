package org.bobpark.documentservice.domain.document.entity.holiday;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class HolidayWorkTimeTest {

    @Test
    void getCalculateWorkTime() {

        HolidayWorkTime workTime =
            HolidayWorkTime.builder()
                .startTime(LocalTime.of(0, 0))
                .endTime(LocalTime.of(3, 0))
                .build();

        assertThat(workTime.getCalculateWorkTime()).isEqualTo(6);

    }
}