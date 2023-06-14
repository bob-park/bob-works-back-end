package org.bobpark.documentservice.domain.document.model.vacation;

import java.time.LocalDate;

import lombok.Builder;

import org.bobpark.documentservice.domain.document.type.VacationSubType;
import org.bobpark.documentservice.domain.document.type.VacationType;

@Builder
public record CreateVacationDocumentRequest(Long typeId,
                                            VacationType vacationType,
                                            VacationSubType vacationSubType,
                                            LocalDate vacationDateFrom,
                                            LocalDate vacationDateTo,
                                            String reason) {
}
