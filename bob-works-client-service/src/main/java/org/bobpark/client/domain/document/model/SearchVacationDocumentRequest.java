package org.bobpark.client.domain.document.model;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class SearchVacationDocumentRequest {
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    private SearchVacationDocumentRequest(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}