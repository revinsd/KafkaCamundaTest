package com.example.KafkaCamundaTest.kafka.producer.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaMessageProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    @Value("${kafka.enabled}")
    private boolean enabled;

    public void publishWithHeaders(String topicName, String message, Map<String, String> headers) {
        if (!enabled) {
            throw new IllegalAccessError("Kafka is disabled");
        }

        log.info("Sending message to topic {}", topicName);
        log.debug("Message: {}, Headers: {}", message, headers);
        var record = new ProducerRecord<String, String>(topicName, message);
        addHeadersToRecord(headers, record);

        kafkaTemplate
                .send(record)
                .addCallback(new ListenableFutureCallback<>() {
                    @Override
                    public void onFailure(Throwable ex) {
                        log.error("Failed sending message to topic {}", topicName, ex);
                    }

                    @Override
                    public void onSuccess(SendResult<String, String> result) {
                        log.info("Message successfully sent to topic {}", topicName);
                    }
                });
    }

    private void addHeadersToRecord(Map<String, String> headers, ProducerRecord<String, String> record) {
        headers.forEach((key, value) -> {
            var header = new RecordHeader(key, value.getBytes());
            record.headers().add(header);
        });
    }
}
