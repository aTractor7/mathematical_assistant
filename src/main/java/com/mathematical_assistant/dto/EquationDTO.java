package com.mathematical_assistant.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EquationDTO {

    private int id;

    @NotBlank(message = "Polynomial shouldn't be empty")
    private String polynomial;

    private String roots;
}
