package org.bobpark.documentservice.domain.document.listener;

import org.bobpark.documentservice.domain.document.entity.Document;

public interface DocumentProvider {

    Document approval(long approvalId, Document document);

    Document reject(long approvalId, Document document, String reason);

    Document canceled(Document document);

    boolean isSupport(Class<? extends Document> clazz);

}
