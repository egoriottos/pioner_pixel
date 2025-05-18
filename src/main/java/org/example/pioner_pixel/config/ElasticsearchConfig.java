package org.example.pioner_pixel.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {
    @Bean
    public JacksonJsonpMapper jacksonJsonpMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // Регистрируем модуль для Java 8 Date/Time
        objectMapper.registerModule(new JavaTimeModule());

        // Отключаем запись дат как timestamp
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        // Настройка формата даты
        objectMapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));

        return new JacksonJsonpMapper(objectMapper);
    }

    @Bean
    public ElasticsearchClient elasticsearchClient(RestClient restClient, JacksonJsonpMapper jacksonJsonpMapper) {
        return new ElasticsearchClient(new RestClientTransport(restClient, jacksonJsonpMapper));
    }
}
