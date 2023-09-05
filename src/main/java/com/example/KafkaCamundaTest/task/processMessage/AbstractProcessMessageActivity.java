package com.example.KafkaCamundaTest.task.processMessage;

import com.example.KafkaCamundaTest.kafka.dto.MessageDto;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import static com.example.KafkaCamundaTest.task.processMessage.ProcessMessageVariable.*;

public abstract class AbstractProcessMessageActivity implements JavaDelegate {

    protected MessageDto getMessage(DelegateExecution delegateExecution) {
        return (MessageDto) delegateExecution.getVariable(MESSAGE.name());
    }

    protected Integer getRestartCount(DelegateExecution delegateExecution) {
        return (Integer) delegateExecution.getVariable(RESTART_COUNT.name());
    }

    protected String getUuid(DelegateExecution delegateExecution) {
        return (String) delegateExecution.getVariable(UUID.name());
    }
}
