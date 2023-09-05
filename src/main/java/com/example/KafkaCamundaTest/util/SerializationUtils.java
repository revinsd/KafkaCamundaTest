package com.example.KafkaCamundaTest.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.type.SerializationException;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class SerializationUtils {
    private final ObjectMapper objectMapper;

    public <T> String serialize(T object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception ex) {
            throw new SerializationException("Can't serialize object [" + object + "]", ex);
        }
    }

    public <T> T convert(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception ex) {
            throw new SerializationException("Can't deserialize object " + json, ex);
        }
    }

    public String fromBytes(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }


}
