package com.example.KafkaCamundaTest.exception;


import com.example.KafkaCamundaTest.kafka.dto.MessageDto;
import com.example.KafkaCamundaTest.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.stereotype.Component;

import static com.example.KafkaCamundaTest.config.kafka.KafkaAdminConfiguration.DEAD_TOPIC_NAME;
import static com.example.KafkaCamundaTest.config.kafka.KafkaAdminConfiguration.EXCEPTION_TOPIC_NAME;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class ExceptionHandlingAspect {
    private final MessageService messageService;
    private final RuntimeService runtimeService;

    @Around("execution(void com.example.KafkaCamundaTest.task.processMessage.ValidateMessageEvent.execute(..))")
    public void handle(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            joinPoint.proceed();
        } catch (ProcessException e) {

            log.warn("Error occurred during processing message create event with id {}", e.getInstanceId());
            processException(
                    e.getMessageDto(),
                    e.getUuid(),
                    e.getRestartCount()
            );
            runtimeService.deleteProcessInstance(e.getInstanceId(), e.getMessage());
        }
    }

    private void processException(MessageDto messageDto, String uuid, int restartCount) {
        if (restartCount <= 0) {
            messageService.sendMessageToTopic(messageDto, DEAD_TOPIC_NAME, 0, uuid);
        } else {
            messageService.sendMessageToTopic(messageDto, EXCEPTION_TOPIC_NAME, --restartCount, uuid);
        }
    }
}
