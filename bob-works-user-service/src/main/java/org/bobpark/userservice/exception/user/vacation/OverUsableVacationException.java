package org.bobpark.userservice.exception.user.vacation;

import java.text.MessageFormat;

public class OverUsableVacationException extends RuntimeException {

    private static final MessageFormat DEFAULT_FORMAT =
        new MessageFormat("has been exceeded usable vacation. (total={0,number,#}, current={1,number,#})");

    public OverUsableVacationException(double total, double current) {
        super(DEFAULT_FORMAT.format(new Object[]{total, current}));
    }
}
