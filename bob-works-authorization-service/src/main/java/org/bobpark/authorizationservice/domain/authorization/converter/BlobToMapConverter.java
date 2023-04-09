package org.bobpark.authorizationservice.domain.authorization.converter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class BlobToMapConverter implements AttributeConverter<Map<String, Object>, byte[]> {
    @Override
    public byte[] convertToDatabaseColumn(Map<String, Object> attribute) {

        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(byteOut)) {

            out.writeObject(attribute);

            return byteOut.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(byte[] dbData) {

        try (ByteArrayInputStream byteIn = new ByteArrayInputStream(dbData);
             ObjectInputStream in = new ObjectInputStream(byteIn)) {

            return (Map<String, Object>)in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
