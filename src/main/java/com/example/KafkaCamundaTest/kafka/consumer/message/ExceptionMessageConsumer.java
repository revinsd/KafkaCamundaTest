package com.example.KafkaCamundaTest.kafka.consumer.message;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.example.KafkaCamundaTest.config.kafka.KafkaAdminConfiguration.EXCEPTION_TOPIC_NAME;

@Slf4j
@Component
@ConditionalOnProperty(name = "kafka.enabled", havingValue = "true")
public class ExceptionMessageConsumer extends AbstractMessageConsumer {

    public ExceptionMessageConsumer() {
        super(EXCEPTION_TOPIC_NAME);
    }

    @Override
    @KafkaListener(topics = EXCEPTION_TOPIC_NAME, groupId = "default")
    public void consume(ConsumerRecord<String, String> record) {
       super.consume(record);
    }

}
