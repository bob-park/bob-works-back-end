package org.bobpark.userservice.exception.support;

import java.text.MessageFormat;

public class NotSupportedException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Not supported.";
    private static final MessageFormat DEFAULT_MESSAGE_FORMAT = new MessageFormat(DEFAULT_MESSAGE + " ({0})");

    public NotSupportedException() {
        super(DEFAULT_MESSAGE);
    }

    public NotSupportedException(String detailMessage) {
        super(DEFAULT_MESSAGE_FORMAT.format(detailMessage));
    }

}
