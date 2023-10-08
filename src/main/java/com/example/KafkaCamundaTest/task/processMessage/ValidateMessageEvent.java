package com.example.KafkaCamundaTest.task.processMessage;

import com.example.KafkaCamundaTest.exception.ProcessException;
import com.example.KafkaCamundaTest.kafka.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import java.util.Random;

import static com.example.KafkaCamundaTest.task.processMessage.ProcessMessageVariable.IS_VALID;
import static java.util.Objects.nonNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class ValidateMessageEvent extends AbstractProcessMessageActivity {
    private final Random random = new Random();

    @Override
    public void execute(DelegateExecution delegateExecution) {
        var message = getMessage(delegateExecution);
        var isValid = isValid(message);
        randomlyThrowException(delegateExecution);
        log.info("Consumed message is {}valid", isValid ? "" : "not ");
        delegateExecution.setVariable(IS_VALID.name(), isValid);
    }

    private boolean isValid(MessageDto messageDto) {
        return nonNull(messageDto.getMessageText());
    }

    private void randomlyThrowException(DelegateExecution delegateExecution){
        if(random.nextBoolean()){
            throw new ProcessException(
                    getUuid(delegateExecution),
                    getMessage(delegateExecution),
                    getRestartCount(delegateExecution),
                    delegateExecution.getProcessInstanceId()
            );
        }
    }


}
