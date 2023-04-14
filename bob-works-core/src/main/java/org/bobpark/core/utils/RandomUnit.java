package org.bobpark.core.utils;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum RandomUnit {

    NUMBER(0, '0', '9'),
    LOWER_CASE(1, 'a', 'z'),
    UPPER_CASE(2, 'A', 'Z');

    private final int unit;
    private final int minChar;
    private final int maxChar;

    RandomUnit(int unit, int minChar, int maxChar) {
        this.unit = unit;
        this.minChar = minChar;
        this.maxChar = maxChar;
    }

    public static RandomUnit find(int unit) {
        return Arrays.stream(RandomUnit.values())
            .filter(item -> item.getUnit() == unit)
            .findAny().orElseThrow();
    }
}
