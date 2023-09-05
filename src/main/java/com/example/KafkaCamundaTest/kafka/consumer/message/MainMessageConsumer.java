package com.example.KafkaCamundaTest.kafka.consumer.message;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.example.KafkaCamundaTest.config.kafka.KafkaAdminConfiguration.MAIN_TOPIC_NAME;

@Slf4j
@Component
@ConditionalOnProperty(name = "kafka.enabled", havingValue = "true")
public class MainMessageConsumer extends AbstractMessageConsumer {

    public MainMessageConsumer() {
        super(MAIN_TOPIC_NAME);
    }

    @KafkaListener(topics = MAIN_TOPIC_NAME, groupId = "default")
    public void consume(ConsumerRecord<String, String> record) {
        super.consume(record);
    }

}
