package com.example.demo.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Random;

@Getter
@Setter
@ToString
@Entity
@Table(name = "Cupons")
@NoArgsConstructor
@AllArgsConstructor
public class Cupom {

    @Id
    @SequenceGenerator(
            name = "cupons_sequence",
            sequenceName = "cupons_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "cupons_sequence"
    )

    private Long id;
    private Raridades raridade;
    private LocalDate validade;

    public Cupom(Raridades raridade, LocalDate validade) {
        this.raridade = raridade;
        this.validade = validade;
    }

    public Cupom(Raridades raridade) {

        int rng = new Random().nextInt(12) + 1;

        this.raridade = raridade;
        this.validade = LocalDate.now().plusMonths(rng);
    }

}
