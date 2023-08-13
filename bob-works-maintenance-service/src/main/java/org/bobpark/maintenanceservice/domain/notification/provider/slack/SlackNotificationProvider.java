package org.bobpark.maintenanceservice.domain.notification.provider.slack;

import static com.google.common.base.Preconditions.*;
import static org.apache.commons.lang3.ObjectUtils.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import org.bobpark.core.exception.ServiceRuntimeException;
import org.bobpark.maintenanceservice.configure.notification.properties.NotificationProperties;
import org.bobpark.maintenanceservice.domain.notification.provider.NotificationProvider;
import org.bobpark.maintenanceservice.domain.notification.provider.NotificationSendMessage;
import org.bobpark.maintenanceservice.domain.notification.provider.slack.message.SlackMessage;
import org.bobpark.maintenanceservice.domain.notification.provider.slack.message.SlackMessageBlock;
import org.bobpark.maintenanceservice.domain.notification.provider.slack.message.SlackMessageBlockText;

@Slf4j
public class SlackNotificationProvider implements NotificationProvider {

    private static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final NotificationProperties properties;
    private final String webHookUrl;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public SlackNotificationProvider(NotificationProperties properties, String webHookUrl, RestTemplate restTemplate,
        ObjectMapper objectMapper) {

        checkArgument(isNotEmpty(properties), "properties must be provided.");
        checkArgument(StringUtils.isNotBlank(webHookUrl), "webHookUrl must be provided.");
        checkArgument(isNotEmpty(restTemplate), "restTemplate must be provided.");

        this.properties = properties;
        this.webHookUrl = webHookUrl;
        this.restTemplate = restTemplate;
        this.objectMapper = defaultIfNull(objectMapper, new ObjectMapper());

        log.debug("created SlackNotificationProvider.");
    }

    @Override
    public void sendMessage(NotificationSendMessage sendMessage) {

        if (!properties.isEnabled()) {
            log.warn("disabled notification.");
            return;
        }

        SlackMessage message = getDefaultMessage(sendMessage.writerId(), sendMessage.contents());
        String body = "";

        try {
            body = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new ServiceRuntimeException(e);
        }

        RequestEntity<String> requestEntity =
            RequestEntity.post(webHookUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);

        ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);

        if (response.getStatusCode().isError()) {
            throw new ServiceRuntimeException("Failed send notification message.");
        }

    }

    private SlackMessage getDefaultMessage(String userId, String contents) {

        SlackMessageBlock header =
            SlackMessageBlock.builder()
                .type("header")
                .text(
                    SlackMessageBlockText.builder()
                        .type("plain_text")
                        .text(userId)
                        .emoji(true)
                        .build())
                .build();

        SlackMessageBlock divider =
            SlackMessageBlock.builder()
                .type("divider")
                .build();

        SlackMessageBlock textContext =
            SlackMessageBlock.builder()
                .type("section")
                .text(SlackMessageBlockText.builder()
                    .type("plain_text")
                    .text(contents)
                    .emoji(true)
                    .build())
                .build();

        SlackMessageBlock dateContext =
            SlackMessageBlock.builder()
                .type("context")
                .elements(Collections.singletonList(
                    SlackMessageBlockText.builder()
                        .type("plain_text")
                        .text(
                            String.format("Created Date: %s", LocalDateTime.now().format(DEFAULT_DATE_TIME_FORMATTER)))
                        .emoji(true)
                        .build()))
                .build();

        List<SlackMessageBlock> blocks =
            Lists.newArrayList(header, divider, textContext, dateContext);

        return new SlackMessage(blocks);
    }
}
