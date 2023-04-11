package org.bobpark.authorizationservice.common.utils.random;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;

@Slf4j
class RandomUtilsTest {

    @Test
    void randomString() {

        String randonString = RandomUtils.randomString(20);

        log.info("randomString={}", randonString);

    }
}