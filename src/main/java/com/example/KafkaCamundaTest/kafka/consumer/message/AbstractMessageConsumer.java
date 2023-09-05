package com.example.KafkaCamundaTest.kafka.consumer.message;

import com.example.KafkaCamundaTest.kafka.KafkaHeader;
import com.example.KafkaCamundaTest.kafka.dto.MessageDto;
import com.example.KafkaCamundaTest.service.camunda.CamundaService;
import com.example.KafkaCamundaTest.util.SerializationUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static com.example.KafkaCamundaTest.kafka.KafkaHeader.RESTART_COUNT_HEADER;
import static com.example.KafkaCamundaTest.kafka.KafkaHeader.UUID_HEADER;
import static com.example.KafkaCamundaTest.service.camunda.CamundaProcessKey.PROCESS_MESSAGE_EVENT;
import static com.example.KafkaCamundaTest.task.processMessage.ProcessMessageVariable.*;

@Slf4j
public abstract class AbstractMessageConsumer {
    @Autowired
    protected CamundaService camundaService;
    @Autowired
    protected SerializationUtils serializationUtils;
    private final String topicName;

    public void consume(ConsumerRecord<String, String> record) {
        log.info("Consuming message from {} topic", topicName);
        var messageJson = record.value();
        var headers = record.headers();
        log.debug("Message: {}, Headers: {}", messageJson, headers);
        try {
            process(messageJson, headers);
        } catch (Exception ex) {
            log.error("Error occurred during consuming message from main topic", ex);
        }
    }

    protected MessageDto getMessageFromJson(String json) {
        return serializationUtils.convert(json, MessageDto.class);
    }

    protected Integer getRestartCount(Headers headers) {
        var restartCountHeader = getHeader(headers, RESTART_COUNT_HEADER);
        var restartCountStringValue = serializationUtils.fromBytes(restartCountHeader.value());
        return Integer.valueOf(restartCountStringValue);
    }

    protected String getUuid(Headers headers) {
        var restartCountHeader = getHeader(headers, UUID_HEADER);
        return serializationUtils.fromBytes(restartCountHeader.value());
    }

    protected Header getHeader(Headers headers, KafkaHeader headerName) {
        return headers.lastHeader(headerName.name());
    }

    protected void process(String messageJson, Headers headers) {
        var restartCount = getRestartCount(headers);
        var uuid = getUuid(headers);
        var message = getMessageFromJson(messageJson);
        camundaService.startProcessAsync(
                PROCESS_MESSAGE_EVENT,
                Map.of(
                        MESSAGE, message,
                        RESTART_COUNT, restartCount,
                        UUID, uuid
                )
        );
    }

    protected AbstractMessageConsumer(String topicName) {
        this.topicName = topicName;
    }

}
