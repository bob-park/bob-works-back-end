package org.bobpark.documentservice.configure;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import org.bobpark.documentservice.common.utils.authentication.AuthenticationUtils;
import org.bobpark.documentservice.domain.document.listener.DelegatingDocumentListener;
import org.bobpark.documentservice.domain.document.listener.DocumentListener;
import org.bobpark.documentservice.domain.document.listener.vacation.VacationDocumentListener;
import org.bobpark.documentservice.domain.document.repository.approval.DocumentApprovalRepository;
import org.bobpark.documentservice.domain.user.feign.client.UserClient;

@RequiredArgsConstructor
@EnableTransactionManagement
@Configuration
public class AppConfiguration {

    private final UserClient userClient;
    private final DocumentApprovalRepository documentApprovalRepository;

    @Bean
    public Jackson2ObjectMapperBuilder configureObjectMapper() {
        // Java time module
        JavaTimeModule jtm = new JavaTimeModule();
        jtm.addDeserializer(
            LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME));
        Jackson2ObjectMapperBuilder builder =
            new Jackson2ObjectMapperBuilder() {
                @Override
                public void configure(@NonNull ObjectMapper objectMapper) {
                    super.configure(objectMapper);
                    objectMapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
                    objectMapper.setVisibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE);
                    objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
                }
            };
        builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        builder.modulesToInstall(jtm);

        return builder;
    }

    @Bean
    public AuthenticationUtils authenticationUtils() {
        return AuthenticationUtils.getInstance();
    }

    @Bean
    public DocumentListener documentListener() {
        DelegatingDocumentListener documentListener = new DelegatingDocumentListener();

        documentListener.addDocumentListener(new VacationDocumentListener(userClient, documentApprovalRepository));

        return documentListener;
    }
}
