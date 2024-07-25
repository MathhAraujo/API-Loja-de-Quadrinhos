package com.example.demo.repository;

import com.example.demo.models.Cupom;
import com.example.demo.models.Raridades;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class CupomRepositoryTest {

    @Autowired
    private CupomRepository cupomRepository;

    @AfterEach
    void tearDown() {
        cupomRepository.deleteAll();
    }

    @Test
    void findById() {

        Long cupomId = 1L;
        Cupom cupom = new Cupom(cupomId, Raridades.COMUM, LocalDate.now().plusYears(1));
        cupomRepository.save(cupom);

        Optional<Cupom> cupomOptional = cupomRepository.findById(cupomId);

        assertThat(cupomOptional.isPresent()).isTrue();
    }

    @Test
    void findByIdFail() {
        Long cupomId = 5L;

        Optional<Cupom> cupomOptional = cupomRepository.findById(cupomId);

        assertThat(cupomOptional.isPresent()).isFalse();
    }
}