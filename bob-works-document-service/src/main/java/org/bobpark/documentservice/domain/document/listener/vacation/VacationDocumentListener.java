package org.bobpark.documentservice.domain.document.listener.vacation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.transaction.annotation.Transactional;

import org.bobpark.documentservice.domain.document.entity.Document;
import org.bobpark.documentservice.domain.document.entity.vacation.VacationDocument;
import org.bobpark.documentservice.domain.document.listener.DocumentListener;
import org.bobpark.documentservice.domain.document.type.DocumentStatus;
import org.bobpark.documentservice.domain.user.feign.client.UserClient;
import org.bobpark.documentservice.domain.user.model.vacation.CancelUserVacationRequest;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VacationDocumentListener implements DocumentListener {

    private final UserClient userClient;

    @Override
    public void canceled(Document document) {

        VacationDocument vacationDocument = (VacationDocument)document;

        if (vacationDocument.getStatus() == DocumentStatus.APPROVE) {

            // update user vacation
            CancelUserVacationRequest cancelRequest =
                CancelUserVacationRequest.builder()
                    .type(vacationDocument.getVacationType())
                    .cancelCount(vacationDocument.getDaysCount())
                    .build();

            userClient.cancelVacation(document.getWriterId(), cancelRequest);

            log.debug("canceled vacation document. (id={}, daysCount={})",
                vacationDocument.getId(),
                vacationDocument.getDaysCount());
        }

        vacationDocument.updateStatus(DocumentStatus.CANCEL);

    }

    @Override
    public boolean isSupport(Class<? extends Document> clazz) {
        return clazz.isAssignableFrom(VacationDocument.class);
    }

}
