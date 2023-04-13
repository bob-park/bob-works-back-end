package org.bobpark.documentservice.domain.document.entity;

import java.time.LocalDate;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.bobpark.documentservice.domain.document.type.DocumentStatus;
import org.bobpark.documentservice.domain.document.type.VacationSubType;
import org.bobpark.documentservice.domain.document.type.VacationType;
import org.bobpark.documentservice.domain.user.entity.User;

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

    public VacationDocument(Long id, DocumentType documentType, User user, DocumentStatus status,
        VacationType vacationType, VacationSubType vacationSubType, LocalDate vacationDateFrom,
        LocalDate vacationDateTo, Double daysCount, String reason) {
        super(id, documentType, user, status);
        this.vacationType = vacationType;
        this.vacationSubType = vacationSubType;
        this.vacationDateFrom = vacationDateFrom;
        this.vacationDateTo = vacationDateTo;
        this.daysCount = daysCount;
        this.reason = reason;
    }
}
