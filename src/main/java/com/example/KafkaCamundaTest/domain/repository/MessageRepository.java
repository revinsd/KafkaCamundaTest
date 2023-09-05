package com.example.KafkaCamundaTest.domain.repository;

import com.example.KafkaCamundaTest.domain.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
