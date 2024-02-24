package org.bobpark.userservice.configure.user;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import org.bobpark.userservice.configure.user.properties.UserProperties;
import org.bobpark.userservice.domain.user.producer.notification.DelegatingNotificationProducer;
import org.bobpark.userservice.domain.user.producer.notification.slack.SlackNotificationProducer;

@RequiredArgsConstructor
@EnableConfigurationProperties(UserProperties.class)
@Configuration
public class UserConfiguration {

    @Bean
    public RestTemplate userNotiRestTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public DelegatingNotificationProducer delegatingNotificationProducer() {
        DelegatingNotificationProducer producer = new DelegatingNotificationProducer();

        producer.addProducer(new SlackNotificationProducer(userNotiRestTemplate(null)));

        return producer;
    }

}
