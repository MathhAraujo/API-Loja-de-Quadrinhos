package com.example.demo.repository;

import com.example.demo.models.Quadrinho;
import com.example.demo.models.Raridades;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class QuadrinhoRepositoryTest {

    @Autowired
    private QuadrinhoRepository quadrinhoRepository;

    @AfterEach
    void tearDown() {
        quadrinhoRepository.deleteAll();
    }

    @Test
    void findById() {

        Long quadrinhoId = 1L;
        Quadrinho quadrinho = new Quadrinho(quadrinhoId, Raridades.RARO, 1, "JJK", "Panini", 0);
        quadrinhoRepository.save(quadrinho);

        Optional<Quadrinho> quadrinhoOptional = quadrinhoRepository.findById(quadrinhoId);

        assertThat(quadrinhoOptional.isPresent()).isTrue();
    }

    @Test
    void findByIdFail() {
        Long quadrinhoId = 5L;

        Optional<Quadrinho> quadrinhoOptional = quadrinhoRepository.findById(quadrinhoId);

        assertThat(quadrinhoOptional.isPresent()).isFalse();
    }


    @Test
    void findByVolumeTituloEditora() {
        Quadrinho quadrinho = new Quadrinho(Raridades.COMUM, 10, "OPM", "Panini");
        quadrinhoRepository.save(quadrinho);

        Optional<Quadrinho> quadrinhoOptional = quadrinhoRepository.findByVolumeTituloEditora(10, "OPM", "Panini");

        assertThat(quadrinhoOptional.isPresent()).isTrue();
    }

    @Test
    void findByVolumeTituloEditoraFailVolume() {
        Quadrinho quadrinho = new Quadrinho(Raridades.COMUM, 10, "OPM", "Panini");
        quadrinhoRepository.save(quadrinho);

        Optional<Quadrinho> quadrinhoOptional = quadrinhoRepository.findByVolumeTituloEditora(15, "OPM", "Panini");

        assertThat(quadrinhoOptional.isPresent()).isFalse();
    }

    @Test
    void findByVolumeTituloEditoraFailTitulo() {
        Quadrinho quadrinho = new Quadrinho(Raridades.COMUM, 10, "OPM", "Panini");
        quadrinhoRepository.save(quadrinho);

        Optional<Quadrinho> quadrinhoOptional = quadrinhoRepository.findByVolumeTituloEditora(10, "JJK", "Panini");

        assertThat(quadrinhoOptional.isPresent()).isFalse();
    }

    @Test
    void findByVolumeTituloEditoraFailEditora() {
        Quadrinho quadrinho = new Quadrinho(Raridades.COMUM, 10, "OPM", "Panini");
        quadrinhoRepository.save(quadrinho);

        Optional<Quadrinho> quadrinhoOptional = quadrinhoRepository.findByVolumeTituloEditora(10, "OPM", "JBC");

        assertThat(quadrinhoOptional.isPresent()).isFalse();
    }
}