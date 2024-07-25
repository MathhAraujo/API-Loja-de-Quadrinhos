package com.example.demo.repository;

import com.example.demo.models.Encomenda;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class EncomendaRepositoryTest {

    @Autowired

    private EncomendaRepository encomendaRepository;

    @AfterEach
    void tearDown() {
        encomendaRepository.deleteAll();
    }

    @Test
    void findById() {

        Long encomendaId = 1L;
        Encomenda encomenda = new Encomenda(encomendaId, LocalDate.now(), 15, 1L, 10L);
        encomendaRepository.save(encomenda);

        Optional<Encomenda> encomendaOptional = encomendaRepository.findById(encomendaId);

        assertThat(encomendaOptional.isPresent()).isTrue();
    }

    @Test
    void findByIdFail() {
        Long encomendaId = 5L;

        Optional<Encomenda> encomendaOptional = encomendaRepository.findById(encomendaId);

        assertThat(encomendaOptional.isPresent()).isFalse();
    }
}