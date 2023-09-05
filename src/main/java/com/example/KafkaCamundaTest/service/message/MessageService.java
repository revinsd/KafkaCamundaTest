package com.example.KafkaCamundaTest.service.message;

import com.example.KafkaCamundaTest.domain.entity.Message;
import com.example.KafkaCamundaTest.domain.repository.MessageRepository;
import com.example.KafkaCamundaTest.kafka.producer.message.KafkaMessageProducer;
import com.example.KafkaCamundaTest.kafka.dto.MessageDto;
import com.example.KafkaCamundaTest.util.SerializationUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

import static com.example.KafkaCamundaTest.kafka.KafkaHeader.RESTART_COUNT_HEADER;
import static com.example.KafkaCamundaTest.kafka.KafkaHeader.UUID_HEADER;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {
    private final MessageRepository messageRepository;
    private final KafkaMessageProducer kafkaMessageProducer;
    private final SerializationUtils serializationUtils;

    @Transactional
    public void createMessage(String messageText, String uuid) {
        log.info("Creating new message");
        var currentDateTime = LocalDateTime.now();
        var message = new Message()
                .setCreationDate(currentDateTime)
                .setText(messageText)
                .setLastUpdateTime(currentDateTime)
                .setUuid(uuid);
        messageRepository.save(message);
    }

    @Transactional
    public void updateMessage(long messageId, String messageText) {
        log.info("Updating message with id {}", messageId);
        var message = messageRepository.findById(messageId).orElseThrow();
        message
                .setLastUpdateTime(LocalDateTime.now())
                .setText(messageText);
        messageRepository.save(message);
    }

    @SneakyThrows
    public void sendMessageToTopic(
            MessageDto messageDto,
            String topicName,
            Integer restartCount,
            String uuid
    ) {
        var messageJson = serializationUtils.serialize(messageDto);
        kafkaMessageProducer.publishWithHeaders(topicName, messageJson, Map.of(
                RESTART_COUNT_HEADER.name(), restartCount.toString(),
                UUID_HEADER.name(), uuid
        ));
    }

}
