package org.bobpark.documentservice.domain.document.listener;

import org.bobpark.documentservice.domain.document.entity.Document;

public interface DocumentListener {

    void canceled(Document d);

    boolean isSupport(Class<? extends Document> clazz);

}
