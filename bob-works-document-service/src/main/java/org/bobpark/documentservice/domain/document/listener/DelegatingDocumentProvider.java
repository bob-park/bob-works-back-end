package org.bobpark.documentservice.domain.document.listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bobpark.documentservice.domain.document.entity.Document;

public class DelegatingDocumentProvider implements DocumentProvider {

    private final List<DocumentProvider> listeners = Collections.synchronizedList(new ArrayList<>());

    @Override
    public Document approval(long approvalId, Document document) {

        Document result = null;

        for (DocumentProvider listener : listeners) {
            Class<? extends Document> clazz = document.getClass();

            if (listener.isSupport(clazz)) {
                result = listener.approval(approvalId, document);
            }
        }

        return result;
    }

    @Override
    public Document reject(long approvalId, Document document, String reason) {

        Document result = null;

        for (DocumentProvider listener : listeners) {
            Class<? extends Document> clazz = document.getClass();

            if (listener.isSupport(clazz)) {
                result = listener.reject(approvalId, document, reason);
            }
        }

        return result;
    }

    @Override
    public Document canceled(Document d) {

        Document result = null;

        for (DocumentProvider listener : listeners) {

            Class<? extends Document> clazz = d.getClass();

            if (listener.isSupport(clazz)) {
                result = listener.canceled(d);
            }

        }

        return result;
    }

    @Override
    public boolean isSupport(Class<? extends Document> clazz) {
        return false;
    }

    public void addDocumentListener(DocumentProvider listener) {
        listeners.add(listener);
    }

}
