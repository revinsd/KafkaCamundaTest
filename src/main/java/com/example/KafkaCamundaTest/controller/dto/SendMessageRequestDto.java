package com.example.KafkaCamundaTest.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SendMessageRequestDto {
    @NotNull
    @NotBlank
    private String messageText;
    @NotNull
    @NotBlank
    private String uuid;
}
