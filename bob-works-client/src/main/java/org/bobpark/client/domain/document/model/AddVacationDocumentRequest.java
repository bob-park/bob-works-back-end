package org.bobpark.client.domain.document.model;

import java.time.LocalDate;

public record AddVacationDocumentRequest(Long typeId,
                                         String vacationType,
                                         String vacationSubType,
                                         LocalDate vacationDateFrom,
                                         LocalDate vacationDateTo,
                                         String reason) {
}
