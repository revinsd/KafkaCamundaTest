package com.example.KafkaCamundaTest.task.processMessage;

import com.example.KafkaCamundaTest.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import static com.example.KafkaCamundaTest.config.kafka.KafkaAdminConfiguration.DEAD_TOPIC_NAME;
import static com.example.KafkaCamundaTest.config.kafka.KafkaAdminConfiguration.EXCEPTION_TOPIC_NAME;

@Component
@RequiredArgsConstructor
public class HandleErrorMessage extends AbstractProcessMessageActivity {
    private final MessageService messageService;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        var message = getMessage(delegateExecution);
        var restartCount = getRestartCount(delegateExecution);
        var uuid = getUuid(delegateExecution);

        if (restartCount <= 0) {
            messageService.sendMessageToTopic(message, DEAD_TOPIC_NAME, 0, uuid);
        } else {
            messageService.sendMessageToTopic(message, EXCEPTION_TOPIC_NAME, --restartCount, uuid);
        }
    }
}
