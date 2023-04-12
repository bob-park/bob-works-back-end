package org.bobpark.core.exception;

import java.text.MessageFormat;

import org.bobpark.core.model.common.Id;

public class NotFoundException extends RuntimeException {

    private static final MessageFormat DEFAULT_FORMAT = new MessageFormat(
        "Not found ''{1}'' with query values ''{0,number,#}''");

    public NotFoundException(String message) {
        super("Not found - " + message);
    }

    public NotFoundException(Class<?> clazz, Object value) {
        super(DEFAULT_FORMAT.format(new Object[] {value, clazz.getSimpleName()}));
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    public NotFoundException(String message, Throwable cause) {
        super("Not found - " + message, cause);
    }

    public <R, T> NotFoundException(Id<R, T> id) {
        super(DEFAULT_FORMAT.format(new Object[] {id.getValue(), id.getClazz().getSimpleName()}));
    }
}
