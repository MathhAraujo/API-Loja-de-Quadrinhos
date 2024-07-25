package com.example.demo.service;

import com.example.demo.Exceptions.InvalidArgumentException;
import com.example.demo.Exceptions.ResourceNotFoundException;
import com.example.demo.models.Cupom;
import com.example.demo.models.Raridades;
import com.example.demo.repository.CupomRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CupomServiceTest {

    @Mock
    private CupomRepository cupomRepository;

    @InjectMocks
    private CupomService cupomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getCupons() {

        Cupom cupom = new Cupom(1L, Raridades.RARO, LocalDate.now().plusYears(2));

        when(cupomRepository.findAll()).thenReturn(List.of(cupom));

        cupomService.getCupons();

        verify(cupomRepository).findAll();

    }

    @Test
    void getCuponsFail() {
        when(cupomRepository.findAll()).thenReturn(Collections.emptyList());

        Exception thrown = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            cupomService.getCupons();
        });
        Assertions.assertEquals("Nenhum cupom encontrado", thrown.getMessage());
    }

    @Test
    void getCupomById() {
        Cupom cupom = new Cupom(1L, Raridades.RARO, LocalDate.now().plusYears(2));

        when(cupomRepository.findById(1L)).thenReturn(Optional.of(cupom));

        cupomService.getCupomById(1L);

        verify(cupomRepository).findById(1L);

    }

    @Test
    void getCupomByIdFail() {
        when(cupomRepository.findById(any())).thenReturn(Optional.empty());

        Exception thrown = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            cupomService.getCupomById(1L);
        });

        Assertions.assertEquals("Cupom não encontrado", thrown.getMessage());

    }

    @Test
    void gerarCupom() {
        cupomService.gerarCupom();

        verify(cupomRepository).save(any());
    }

    @Test
    void gerarCupomCustomizado() {
        Cupom cupomSaved = cupomService.gerarCupomCustomizado(Raridades.RARO, LocalDate.now().plusYears(2));

        verify(cupomRepository).save(cupomSaved);
    }

    @Test
    void gerarCupomCustomizadoFail() {

        Exception thrown = Assertions.assertThrows(InvalidArgumentException.class, () -> {
            cupomService.gerarCupomCustomizado(Raridades.RARO, LocalDate.now().minusYears(2));
        });

        Assertions.assertEquals("Validade inválida", thrown.getMessage());

    }

    @Test
    void deletarCupom() {
        when(cupomRepository.existsById(1L)).thenReturn(true);

        cupomService.deletarCupom(1L);

        verify(cupomRepository).deleteById(1L);

    }

    @Test
    void deletarCupomFail() {

        Exception thrown = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            cupomService.deletarCupom(any());
        });

        Assertions.assertEquals("Cupom não encontrado", thrown.getMessage());
    }
}