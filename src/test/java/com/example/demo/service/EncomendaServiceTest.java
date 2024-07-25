package com.example.demo.service;

import com.example.demo.Exceptions.InvalidArgumentException;
import com.example.demo.Exceptions.ResourceNotFoundException;
import com.example.demo.models.Cupom;
import com.example.demo.models.Encomenda;
import com.example.demo.models.Quadrinho;
import com.example.demo.models.Raridades;
import com.example.demo.repository.CupomRepository;
import com.example.demo.repository.EncomendaRepository;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EncomendaServiceTest {

    @Mock
    private CupomService cupomService;
    @Mock
    private QuadrinhoService quadrinhoService;
    @Mock
    private CupomRepository cupomRepository;
    @Mock
    private EncomendaRepository encomendaRepository;

    @InjectMocks
    private EncomendaService encomendaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getEncomendas() {
        Encomenda encomenda = new Encomenda(1L, LocalDate.now(), 100, 1L, 2L);
        when(encomendaRepository.findAll()).thenReturn((List.of(encomenda)));

        encomendaService.getEncomendas();

        verify(encomendaRepository).findAll();

    }

    @Test
    void getEncomendasFail() {
        when(encomendaRepository.findAll()).thenReturn((Collections.emptyList()));

        Exception thrown = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            encomendaService.getEncomendas();
        });
        Assertions.assertEquals("Nenhuma encomenda encontrada", thrown.getMessage());
    }

    @Test
    void getEncomendaById() {

        Encomenda encomenda = new Encomenda(1L, LocalDate.now(), 100, 1L, 2L);
        when(encomendaRepository.findById(1L)).thenReturn((Optional.of(encomenda)));

        encomendaService.getEncomendaById(1L);

        verify(encomendaRepository).findById(1L);


    }

    @Test
    void getEncomendaByIdFail() {

        when(encomendaRepository.findById(anyLong())).thenReturn((Optional.empty()));

        Exception thrown = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            encomendaService.getEncomendaById(1L);
        });

        Assertions.assertEquals("Encomenda não encontrada", thrown.getMessage());

    }

    @Test
    void novaEncomenda() {
        Quadrinho quadrinho = new Quadrinho(1L, Raridades.RARO, 3, "JJK", "NewPop", 100);
        Cupom cupom = new Cupom(2L, Raridades.RARO, LocalDate.now().plusYears(2));
        when(quadrinhoService.getQuadrinhoById(anyLong())).thenReturn(quadrinho);
        when(cupomService.getCupomById(anyLong())).thenReturn(cupom);

        Encomenda encomenda = encomendaService.novaEncomenda(10, 1L, 2L);

        verify(cupomService).deletarCupom(cupom.getId());
        verify(encomendaRepository).save(encomenda);
        verify(quadrinhoService).atualizaEstoque(quadrinho.getId(), quadrinho.getEstoque() - encomenda.getQuantidade());

    }

    @Test
    void novaEncomendaSemCupom() {
        Quadrinho quadrinho = new Quadrinho(1L, Raridades.RARO, 3, "JJK", "NewPop", 100);
        when(quadrinhoService.getQuadrinhoById(anyLong())).thenReturn(quadrinho);

        Encomenda encomenda = encomendaService.novaEncomenda(10, 1L, null);

        verify(encomendaRepository).save(encomenda);
        verify(quadrinhoService).atualizaEstoque(quadrinho.getId(), quadrinho.getEstoque() - encomenda.getQuantidade());

    }

    @Test
    void novaEncomendaFailRaridadesDiferentes() {

        Quadrinho quadrinho = new Quadrinho(1L, Raridades.RARO, 3, "JJK", "NewPop", 100);
        Cupom cupom = new Cupom(2L, Raridades.COMUM, LocalDate.now().plusYears(2));
        when(quadrinhoService.getQuadrinhoById(anyLong())).thenReturn(quadrinho);
        when(cupomService.getCupomById(anyLong())).thenReturn(cupom);


        Exception thrown = Assertions.assertThrows(InvalidArgumentException.class, () -> {
            encomendaService.novaEncomenda(10, 1L, 2L);
        });

        Assertions.assertEquals("Quadrinho e cupom possuem raridades diferentes", thrown.getMessage());
    }

    @Test
    void novaEncomendaFailForaDeEstoque() {

        Quadrinho quadrinho = new Quadrinho(1L, Raridades.RARO, 3, "JJK", "NewPop", 0);
        when(quadrinhoService.getQuadrinhoById(anyLong())).thenReturn(quadrinho);


        Exception thrown = Assertions.assertThrows(InvalidArgumentException.class, () -> {
            encomendaService.novaEncomenda(10, 1L, null);
        });

        Assertions.assertEquals("Quadrinho fora de estoque", thrown.getMessage());

    }

    @Test
    void novaEncomendaFailPedidoMaiorQueOEstoque() {
        Quadrinho quadrinho = new Quadrinho(1L, Raridades.RARO, 3, "JJK", "NewPop", 1);
        when(quadrinhoService.getQuadrinhoById(anyLong())).thenReturn(quadrinho);


        Exception thrown = Assertions.assertThrows(InvalidArgumentException.class, () -> {
            encomendaService.novaEncomenda(10, 1L, null);
        });

        Assertions.assertEquals("Pedido maior que estoque disponível", thrown.getMessage());
    }

    @Test
    void deletarEncomenda() {
        when(encomendaRepository.existsById(anyLong())).thenReturn(true);

        encomendaService.deletarEncomenda(1L);

        verify(encomendaRepository).deleteById(1L);

    }

    @Test
    void deletarEncomendaFail() {
        Exception thrown = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            encomendaService.deletarEncomenda(1L);
        });

        Assertions.assertEquals("Encomenda não encontrada", thrown.getMessage());
    }
}