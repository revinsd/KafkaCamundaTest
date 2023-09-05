package com.example.KafkaCamundaTest.task.processMessage;

import com.example.KafkaCamundaTest.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateMessage extends AbstractProcessMessageActivity {
    private final MessageService messageService;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        var message = getMessage(delegateExecution);
        var uuid = getUuid(delegateExecution);
        messageService.createMessage(message.getMessageText(), uuid);
    }
}
