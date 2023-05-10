package org.bobpark.documentservice.domain.document.entity.vacation;

import static com.google.common.base.Preconditions.*;
import static org.apache.commons.lang3.ObjectUtils.*;

import java.time.LocalDate;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.bobpark.documentservice.domain.document.entity.Document;
import org.bobpark.documentservice.domain.document.entity.DocumentType;
import org.bobpark.documentservice.domain.document.type.DocumentStatus;
import org.bobpark.documentservice.domain.document.type.VacationSubType;
import org.bobpark.documentservice.domain.document.type.VacationType;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "vacation_documents")
@DiscriminatorValue("VACATION")
public class VacationDocument extends Document {

    @Enumerated(EnumType.STRING)
    private VacationType vacationType;

    @Enumerated(EnumType.STRING)
    private VacationSubType vacationSubType;

    private LocalDate vacationDateFrom;
    private LocalDate vacationDateTo;
    private Double daysCount;
    private String reason;

    @Builder
    private VacationDocument(Long id, DocumentType documentType, Long writerId, Long teamId, DocumentStatus status,
        VacationType vacationType, VacationSubType vacationSubType, LocalDate vacationDateFrom,
        LocalDate vacationDateTo, String reason) {
        super(id, documentType, writerId, teamId, status);

        checkArgument(isNotEmpty(vacationType), "vacationType must be provided.");
        checkArgument(isNotEmpty(vacationDateFrom), "vacationDateFrom must be provided.");
        checkArgument(isNotEmpty(vacationDateTo), "vacationDateTo must be provided.");
        checkArgument(isNotEmpty(reason), "reason must be provided.");

        checkArgument(vacationSubType == null || vacationDateFrom.isEqual(vacationDateTo),
            "If vacationSubType exist, vacationDateFrom and vacationDateTo must be equals.");

        checkArgument(vacationDateFrom.isBefore(vacationDateTo) || vacationDateFrom.isEqual(vacationDateTo),
            "vacationDateTo must be greater than vacationDateFrom");

        this.vacationType = vacationType;
        this.vacationSubType = vacationSubType;
        this.vacationDateFrom = vacationDateFrom;
        this.vacationDateTo = vacationDateTo;
        this.daysCount = setDayCount(vacationSubType, vacationDateFrom, vacationDateTo);
        this.reason = reason;
    }

    private double setDayCount(VacationSubType subType, LocalDate from, LocalDate to) {

        if (subType != null) {
            return 0.5;
        }

        return to.getDayOfYear() - from.getDayOfYear() + 1f;
    }
}
