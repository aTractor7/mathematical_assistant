package com.mathematical_assistant.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "equation")
public class Equation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "polynomial")
    private String polynomial;

    @Column(name = "power")
    private int power;

    @Column(name = "roots")
    private String roots;
}
