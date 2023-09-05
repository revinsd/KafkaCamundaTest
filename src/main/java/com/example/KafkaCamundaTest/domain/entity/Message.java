package com.example.KafkaCamundaTest.domain.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "MESSAGES")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String uuid;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    private LocalDateTime lastUpdateTime;
}
