package org.bobpark.documentservice.domain.document.listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bobpark.documentservice.domain.document.entity.Document;

public class DelegatingDocumentListener implements DocumentListener {

    private final List<DocumentListener> listeners = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void canceled(Document d) {

        for (DocumentListener listener : listeners) {

            Class<? extends Document> clazz = d.getClass();

            if (listener.isSupport(clazz)) {
                listener.canceled(d);
            }

        }

    }

    @Override
    public boolean isSupport(Class<? extends Document> clazz) {
        return false;
    }

    public void addDocumentListener(DocumentListener listener) {
        listeners.add(listener);
    }

}
