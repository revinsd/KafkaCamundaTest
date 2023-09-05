package com.example.KafkaCamundaTest.domain.repository;

import com.example.KafkaCamundaTest.domain.entity.MessageIncident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageIncidentRepository extends JpaRepository<MessageIncident, Long> {
}
