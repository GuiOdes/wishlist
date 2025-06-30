package com.guiodes.wishlist.infra.configs;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.List;
import java.util.UUID;

@Configuration
public class MongoConfig {

    @Bean
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(List.of(
                new UUIDToStringConverter(),
                new StringToUUIDConverter()
        ));
    }

    @WritingConverter
    static class UUIDToStringConverter implements Converter<UUID, String> {
        public String convert(UUID source) {
            return source.toString();
        }
    }

    @ReadingConverter
    static class StringToUUIDConverter implements Converter<String, UUID> {
        public UUID convert(String source) {
            return UUID.fromString(source);
        }
    }
}
