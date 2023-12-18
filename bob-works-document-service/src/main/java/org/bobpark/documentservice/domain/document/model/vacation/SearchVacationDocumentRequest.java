package org.bobpark.documentservice.domain.document.model.vacation;

import java.time.LocalDate;

public record SearchVacationDocumentRequest(LocalDate startDate,
                                            LocalDate endDate) {
}
