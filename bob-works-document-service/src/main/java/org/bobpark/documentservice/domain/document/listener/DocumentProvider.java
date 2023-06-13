package org.bobpark.documentservice.domain.document.listener;

import java.util.List;

import org.bobpark.documentservice.domain.document.entity.Document;
import org.bobpark.documentservice.domain.document.type.DocumentStatus;

public interface DocumentProvider {

    List<DocumentStatus> ALLOW_STATUS = List.of(DocumentStatus.WAITING, DocumentStatus.PROCEEDING);

    Document approval(long approvalId, Document document);

    Document reject(long approvalId, Document document, String reason);

    Document canceled(Document document);

    boolean isSupport(Class<? extends Document> clazz);

    default void allowStatus(DocumentStatus status) {
        if (!ALLOW_STATUS.contains(status)) {
            throw new IllegalStateException("Invalid status. (" + status + ")");
        }
    }

}
