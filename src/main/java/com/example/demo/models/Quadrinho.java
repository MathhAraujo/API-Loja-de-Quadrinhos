package com.example.demo.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "Quadrinhos")
@NoArgsConstructor
@AllArgsConstructor
public class Quadrinho {

    @Id
    @SequenceGenerator(
            name = "quadrinhos_sequence",
            sequenceName = "quadrinhos_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "quadrinhos_sequence"
    )
    private Long id;

    private Raridades raridade;
    private int volume;
    private String titulo;
    private String editora;
    private int estoque;

    public Quadrinho(Raridades raridade, int volume, String titulo, String editora) {
        this.raridade = raridade;
        this.volume = volume;
        this.titulo = titulo;
        this.editora = editora;
        this.estoque = 0;
    }

    public Quadrinho(Raridades raridade, int volume, String titulo, String editora, int estoque) {
        this.raridade = raridade;
        this.volume = volume;
        this.titulo = titulo;
        this.editora = editora;
        this.estoque = estoque;
    }

}
