package org.bobpark.documentservice.domain.document.entity.converter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

@Converter
public class ParseNumberArrayConverter implements AttributeConverter<List<Long>, String> {

    private static final String SEPARATOR = ",";

    @Override
    public String convertToDatabaseColumn(List<Long> attribute) {

        if (attribute == null) {
            return null;
        }

        return StringUtils.join(attribute, SEPARATOR);

    }

    @Override
    public List<Long> convertToEntityAttribute(String dbData) {

        if (StringUtils.isBlank(dbData)) {
            return Collections.emptyList();
        }

        return Arrays.stream(dbData.split(SEPARATOR))
            .map(NumberUtils::toLong)
            .toList();
    }
}
