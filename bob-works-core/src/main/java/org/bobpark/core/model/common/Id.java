package org.bobpark.core.model.common;

import static com.google.common.base.Preconditions.*;
import static org.apache.commons.lang3.ObjectUtils.*;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Id<C, V> {

    private final V value;
    private final Class<C> clazz;

    private Id(Class<C> clazz, V value) {

        checkArgument(isNotEmpty(value), "value must be provided");
        checkArgument(isNotEmpty(clazz), "clazz must be provided");

        this.value = value;
        this.clazz = clazz;
    }

    public static <C, V> Id<C, V> of(Class<C> clazz, V value) {
        return new Id<>(clazz, value);
    }
}
