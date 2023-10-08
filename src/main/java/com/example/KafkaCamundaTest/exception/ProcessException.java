package com.example.KafkaCamundaTest.exception;

import com.example.KafkaCamundaTest.kafka.dto.MessageDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProcessException extends RuntimeException{
    private final String uuid;
    private final MessageDto messageDto;
    private final int restartCount;
    private final String instanceId;
}
