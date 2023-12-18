package org.bobpark.client.domain.document.model;

import java.time.LocalDate;

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
}