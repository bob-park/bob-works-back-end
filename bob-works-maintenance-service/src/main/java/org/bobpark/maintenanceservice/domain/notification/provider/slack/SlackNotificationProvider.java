package org.bobpark.maintenanceservice.domain.notification.provider.slack;

import static com.google.common.base.Preconditions.*;
import static org.apache.commons.lang3.ObjectUtils.*;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;

import org.bobpark.core.exception.ServiceRuntimeException;
import org.bobpark.maintenanceservice.configure.notification.properties.NotificationProperties;
import org.bobpark.maintenanceservice.domain.notification.provider.NotificationProvider;
import org.bobpark.maintenanceservice.domain.notification.provider.slack.command.SlackSendCommand;
import org.bobpark.maintenanceservice.domain.notification.provider.slack.message.SlackMessage;
import org.bobpark.maintenanceservice.domain.notification.provider.slack.message.SlackMessageBlock;
import org.bobpark.maintenanceservice.domain.notification.provider.slack.message.SlackMessageBlockText;

@Slf4j
public class SlackNotificationProvider implements NotificationProvider<SlackSendCommand> {

    private final NotificationProperties properties;
    private final String webHookUrl;
    private final RestTemplate restTemplate;

    public SlackNotificationProvider(NotificationProperties properties, String webHookUrl, RestTemplate restTemplate) {

        checkArgument(isNotEmpty(properties), "properties must be provided.");
        checkArgument(StringUtils.isNotBlank(webHookUrl), "webHookUrl must be provided.");
        checkArgument(isNotEmpty(restTemplate), "restTemplate must be provided.");

        this.properties = properties;
        this.webHookUrl = webHookUrl;
        this.restTemplate = restTemplate;

        log.debug("created SlackNotificationProvider.");
    }

    @Override
    public void sendMessage(SlackSendCommand sendCommand) {

        if (!properties.isEnabled()) {
            log.warn("disabled notification.");
            return;
        }

        RequestEntity<SlackMessage> requestEntity =
            RequestEntity.post(webHookUrl)
                .body(getDefaultMessage(sendCommand.userId(), sendCommand.contents()));

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

        SlackMessageBlock section =
            SlackMessageBlock.builder()
                .type("section")
                .text(SlackMessageBlockText.builder()
                    .type("mrkdwn")
                    .text(contents)
                    .build())
                .build();

        List<SlackMessageBlock> blocks = Lists.newArrayList(header, divider, section);

        return new SlackMessage(blocks);
    }
}
