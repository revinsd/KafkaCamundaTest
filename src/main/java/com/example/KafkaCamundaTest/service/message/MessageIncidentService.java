package com.example.KafkaCamundaTest.service.message;

import com.example.KafkaCamundaTest.domain.entity.MessageIncident;
import com.example.KafkaCamundaTest.domain.repository.MessageIncidentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageIncidentService {
    private final MessageIncidentRepository repository;

    @Transactional
    public void registerIncident(String messageJson, String uuid) {
        log.info("Registering incident");
        var incident = new MessageIncident()
                .setIncidentDateTime(LocalDateTime.now())
                .setInvalidMessageJson(messageJson)
                .setUuid(uuid);
        repository.save(incident);
    }
}
