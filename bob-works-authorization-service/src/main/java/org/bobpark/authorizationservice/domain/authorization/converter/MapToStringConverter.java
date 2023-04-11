package org.bobpark.authorizationservice.domain.authorization.converter;

import static org.springframework.util.StringUtils.*;

import java.util.Collections;
import java.util.Map;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@Converter
public class MapToStringConverter implements AttributeConverter<Map<String, Object>, String> {

    private final ObjectMapper om = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, Object> attribute) {

        if (attribute == null) {
            return "{}";
        }

        try {
            return om.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            log.error("Failed parse json - {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String dbData) {

        if (hasText(dbData)) {
            try {
                return om.readValue(dbData, new TypeReference<>() {
                });
            } catch (JsonProcessingException e) {
                log.error("Failed parse json - {}", e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }

        return Collections.emptyMap();
    }
}
