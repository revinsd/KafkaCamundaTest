package com.example.KafkaCamundaTest.service.camunda;

import com.example.KafkaCamundaTest.task.processMessage.ProcessMessageVariable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CamundaService {
    private final TaskService taskService;
    private final RuntimeService runtimeService;

    public void startProcessAsync(CamundaProcessKey processKey, Map<ProcessMessageVariable, Object> variables) {
        CompletableFuture.runAsync(() -> startProcess(processKey, variables));
    }

    public void startProcess(CamundaProcessKey processKey, Map<ProcessMessageVariable, Object> variables) {
        var finalVariables = variables.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().name(),
                        Map.Entry::getValue
                ));
        var processId = runtimeService.startProcessInstanceByKey(processKey.name(), finalVariables).getId();
        var taskId = taskService
                .createTaskQuery()
                .active()
                .processInstanceId(processId)
                .singleResult()
                .getId();

        taskService.resolveTask(taskId);
        taskService.complete(taskId);
    }
}
