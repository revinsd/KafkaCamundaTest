package com.example.KafkaCamundaTest.kafka.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class MessageDto implements Serializable {
    private String messageText;
}
