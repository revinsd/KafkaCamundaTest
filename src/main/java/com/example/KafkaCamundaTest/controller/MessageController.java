package com.example.KafkaCamundaTest.controller;

import com.example.KafkaCamundaTest.controller.dto.SendMessageRequestDto;
import com.example.KafkaCamundaTest.service.message.MessageMappers;
import com.example.KafkaCamundaTest.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.example.KafkaCamundaTest.config.kafka.KafkaAdminConfiguration.MAIN_TOPIC_NAME;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/message")
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/send/main")
    public void sendToMainTopic(@RequestBody @Valid SendMessageRequestDto requestDto) {
        var message = MessageMappers.toMessageDto(requestDto);
        var uuid = requestDto.getUuid();

        messageService.sendMessageToTopic(message, MAIN_TOPIC_NAME, 2, uuid);
    }
}
