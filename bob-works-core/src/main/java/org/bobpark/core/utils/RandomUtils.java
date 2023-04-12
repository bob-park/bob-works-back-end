package org.bobpark.core.utils;

import java.util.Random;

public interface RandomUtils {

    Random random = new Random();

    static String randomString(int limit) {

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < limit; i++) {

            int unit = random.nextInt(3);

            RandomUnit randomUnit = RandomUnit.find(unit);

            int min = randomUnit.getMinChar();
            int max = randomUnit.getMaxChar();

            int randomChar = random.nextInt(max + 1 - min) + min;
            builder.append((char)randomChar);
        }

        return builder.toString();
    }
}
