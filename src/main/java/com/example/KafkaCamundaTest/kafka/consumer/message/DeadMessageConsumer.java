package com.example.KafkaCamundaTest.kafka.consumer.message;

import com.example.KafkaCamundaTest.service.message.MessageIncidentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.example.KafkaCamundaTest.config.kafka.KafkaAdminConfiguration.DEAD_TOPIC_NAME;

@Component
@Slf4j
@ConditionalOnProperty(name = "kafka.enabled", havingValue = "true")
public class DeadMessageConsumer extends AbstractMessageConsumer {
    private final MessageIncidentService messageIncidentService;

    public DeadMessageConsumer(MessageIncidentService messageIncidentService) {
        super(DEAD_TOPIC_NAME);
        this.messageIncidentService = messageIncidentService;
    }

    @KafkaListener(topics = DEAD_TOPIC_NAME, groupId = "default")
    public void consume(ConsumerRecord<String, String> record) {
        super.consume(record);
    }

    @Override
    protected void process(String messageJson, Headers headers) {
        var uuid = getUuid(headers);
        messageIncidentService.registerIncident(messageJson, uuid);
    }

}
