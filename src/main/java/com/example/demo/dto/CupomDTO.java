package com.example.demo.dto;

import com.example.demo.models.Raridades;

import java.time.LocalDate;

public record CupomDTO(Raridades raridade, LocalDate validade) {
}
