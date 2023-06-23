package org.bobpark.client.domain.document.model;

import java.time.LocalDate;
import java.util.List;

public record AddVacationDocumentRequest(Long typeId,
                                         String vacationType,
                                         String vacationSubType,
                                         LocalDate vacationDateFrom,
                                         LocalDate vacationDateTo,
                                         String reason,
                                         List<Long> useAlternativeVacationIds) {
}
