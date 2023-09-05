package com.example.KafkaCamundaTest.service.message;

import com.example.KafkaCamundaTest.controller.dto.SendMessageRequestDto;
import com.example.KafkaCamundaTest.kafka.dto.MessageDto;

public abstract class MessageMappers {

    public static MessageDto toMessageDto(SendMessageRequestDto reqDto) {
        return new MessageDto()
                .setMessageText(reqDto.getMessageText());
    }
}
