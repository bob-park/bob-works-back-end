package org.bobpark.userservice.configure.feign;

import lombok.RequiredArgsConstructor;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import feign.Logger;
import feign.Logger.Level;

@RequiredArgsConstructor
@Configuration
public class FeignConfiguration {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Logger.Level feinLogLevel() {
        return Level.BASIC;
    }
}
