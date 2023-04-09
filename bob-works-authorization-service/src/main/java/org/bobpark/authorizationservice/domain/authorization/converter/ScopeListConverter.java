package org.bobpark.authorizationservice.domain.authorization.converter;

import static org.springframework.util.StringUtils.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ScopeListConverter implements AttributeConverter<List<String>, String> {

    private static final String DEFAULT_DELIMITER = ",";

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {

        if(attribute == null){
            return "";
        }

        return String.join(DEFAULT_DELIMITER, attribute);
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {

        if (!hasText(dbData)) {
            return Collections.emptyList();
        }

        String[] scopes = dbData.split(DEFAULT_DELIMITER);

        return Arrays.stream(scopes)
            .map(String::trim)
            .toList();
    }
}
