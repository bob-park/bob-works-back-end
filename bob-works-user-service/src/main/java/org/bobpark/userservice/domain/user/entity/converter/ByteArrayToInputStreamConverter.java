package org.bobpark.userservice.domain.user.entity.converter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import org.springframework.util.StreamUtils;

import org.bobpark.core.exception.NotFoundException;
import org.bobpark.core.exception.ServiceRuntimeException;

@Converter
public class ByteArrayToInputStreamConverter implements AttributeConverter<InputStream, byte[]> {

    @Override
    public byte[] convertToDatabaseColumn(InputStream data) {

        if (data == null) {
            throw new NotFoundException("No exist input data.");
        }

        try {
            return StreamUtils.copyToByteArray(data);
        } catch (IOException e) {
            throw new ServiceRuntimeException(e);
        }
    }

    @Override
    public InputStream convertToEntityAttribute(byte[] data) {

        if (data == null) {
            return null;
        }

        return new ByteArrayInputStream(data);
    }
}
