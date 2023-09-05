package com.example.KafkaCamundaTest.domain.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Accessors(chain = true)
@Table(name = "INCIDENTS")
public class MessageIncident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String uuid;

    @Column(nullable = false)
    private LocalDateTime incidentDateTime;

    @Column(nullable = false)
    private String invalidMessageJson;


}
