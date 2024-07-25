package com.example.demo.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity(name = "Encomendas")
@Table(name = "Encomendas")
@NoArgsConstructor
@AllArgsConstructor
public class Encomenda {

    @Id
    @SequenceGenerator(
            name = "encomendas_sequence",
            sequenceName = "encomendas_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "encomendas_sequence"
    )
    private Long id;
    private LocalDate dataDoPedido = LocalDate.now();
    private int quantidade;
    private Long quadrinhoId;
    private Long cupomId;

    public Encomenda(int quantidade, Long quadrinhoId, Long cupomId) {
        this.quantidade = quantidade;
        this.quadrinhoId = quadrinhoId;
        this.cupomId = cupomId;
    }

    public Encomenda(int quantidade, Long quadrinhoId) {
        this.quantidade = quantidade;
        this.quadrinhoId = quadrinhoId;
    }

}
