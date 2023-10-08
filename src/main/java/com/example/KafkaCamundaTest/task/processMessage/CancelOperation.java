package com.example.KafkaCamundaTest.task.processMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import static com.example.KafkaCamundaTest.service.camunda.CamundaProcessKey.PROCESS_MESSAGE_EVENT;

@Component
@RequiredArgsConstructor
@Slf4j
public class CancelOperation extends AbstractProcessMessageActivity {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.error(
                "Canceling {} with id {} due to validation failure",
                PROCESS_MESSAGE_EVENT,
                delegateExecution.getActivityInstanceId()
        );
    }
}
