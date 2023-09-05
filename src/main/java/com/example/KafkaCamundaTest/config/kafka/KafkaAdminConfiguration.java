package com.example.KafkaCamundaTest.config.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;

import static org.apache.kafka.clients.admin.AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG;

@Configuration
@ConditionalOnProperty(name = "kafka.enabled", havingValue = "true")
@Slf4j
public class KafkaAdminConfiguration {
    public static final String MAIN_TOPIC_NAME = "main";
    public static final String EXCEPTION_TOPIC_NAME = "exception";
    public static final String DEAD_TOPIC_NAME = "dead";
    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        var config = new HashMap<String, Object>();
        config.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        return new KafkaAdmin(config);
    }

    @Bean
    public NewTopic mainTopic() {
        return new NewTopic(MAIN_TOPIC_NAME, 1, (short) 1);
    }

    @Bean
    public NewTopic exceptionTopic() {
        return new NewTopic(EXCEPTION_TOPIC_NAME, 1, (short) 1);
    }

    @Bean
    public NewTopic deadLetterTopic() {
        return new NewTopic(DEAD_TOPIC_NAME, 1, (short) 1);
    }
}
