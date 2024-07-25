package com.example.demo.service;

import com.example.demo.Exceptions.InvalidArgumentException;
import com.example.demo.Exceptions.ResourceNotFoundException;
import com.example.demo.models.Quadrinho;
import com.example.demo.models.Raridades;
import com.example.demo.repository.QuadrinhoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class QuadrinhoServiceTest {

    @Mock
    QuadrinhoRepository quadrinhoRepository;
    @InjectMocks
    QuadrinhoService quadrinhoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getQuadrinhos() {
        Quadrinho quadrinho = new Quadrinho(1L, Raridades.RARO, 3, "JJK", "NewPop", 100);
        when(quadrinhoRepository.findAll()).thenReturn(List.of(quadrinho));

        quadrinhoService.getQuadrinhos();

        verify(quadrinhoRepository).findAll();
    }

    @Test
    void getQuadrinhosFail() {
        when(quadrinhoRepository.findAll()).thenReturn(Collections.emptyList());

        Exception thrown = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            quadrinhoService.getQuadrinhos();
        });
        Assertions.assertEquals("Nenhum quadrinho encontrado", thrown.getMessage());
    }

    @Test
    void getQuadrinhoById() {
        Quadrinho quadrinho = new Quadrinho(1L, Raridades.RARO, 3, "JJK", "NewPop", 100);
        when(quadrinhoRepository.findById(1L)).thenReturn(Optional.of(quadrinho));

        quadrinhoService.getQuadrinhoById(1L);

        verify(quadrinhoRepository).findById(1L);
    }

    @Test
    void getQuadrinhoByIdFail() {
        when(quadrinhoRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception thrown = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            quadrinhoService.getQuadrinhoById(1L);
        });
        Assertions.assertEquals("Quadrinho não encontrado", thrown.getMessage());
    }

    @Test
    void cadastraQuadrinho() {
        when(quadrinhoRepository.findByVolumeTituloEditora(anyInt(), anyString(), anyString())).thenReturn(Optional.empty());

        Quadrinho quadrinho = quadrinhoService.cadastraQuadrinho(Raridades.COMUM, 1, "JJK", "Panini");

        verify(quadrinhoRepository).save(quadrinho);
    }

    @Test
    void cadastraQuadrinhoFail() {
        Quadrinho quadrinho = new Quadrinho(1L, Raridades.RARO, 3, "JJK", "NewPop", 100);
        when(quadrinhoRepository.findByVolumeTituloEditora(3, "JJK", "NewPop")).thenReturn(Optional.of(quadrinho));

        Exception thrown = Assertions.assertThrows(InvalidArgumentException.class, () -> {
            quadrinhoService.cadastraQuadrinho(Raridades.RARO, 3, "JJK", "NewPop");
        });
        Assertions.assertEquals("Quadrinho já cadastrado", thrown.getMessage());
    }

    @Test
    void atualizaQuadrinho() {
        Quadrinho quadrinho = new Quadrinho(1L, Raridades.RARO, 3, "JJK", "NewPop", 100);
        when(quadrinhoRepository.findById(1L)).thenReturn(Optional.of(quadrinho));
        when(quadrinhoRepository.findByVolumeTituloEditora(4, "OPM", "Panini")).thenReturn((Optional.empty()));
        when(quadrinhoRepository.findByVolumeTituloEditora(4, "OPM", "Panini")).thenReturn((Optional.empty()));
        when(quadrinhoRepository.findByVolumeTituloEditora(4, "OPM", "Panini")).thenReturn((Optional.empty()));

        Quadrinho quadrinhoNew = quadrinhoService.atualizaQuadrinho(1L, 4, "OPM", "Panini");

        assertThat(quadrinhoNew.getVolume()).isEqualTo(4);
        assertThat(quadrinhoNew.getTitulo()).isEqualTo("OPM");
        assertThat(quadrinhoNew.getEditora()).isEqualTo("Panini");


    }

    @Test
    void atualizaQuadrinhoFailQuadrinhoNaoEncontrado() {
        Exception thrown = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            quadrinhoService.atualizaQuadrinho(1L, null, null, null);
        });
        Assertions.assertEquals("Quadrinho não encontrado", thrown.getMessage());
    }

    @Test
    void atualizaQuadrinhoFailVolume() {
        Quadrinho quadrinho = new Quadrinho(1L, Raridades.RARO, 3, "JJK", "NewPop", 100);
        when(quadrinhoRepository.findById(1L)).thenReturn(Optional.of(quadrinho));
        when(quadrinhoRepository.findByVolumeTituloEditora(1, "JJK", "NewPop")).thenReturn((Optional.of(quadrinho)));

        Exception thrown = Assertions.assertThrows(InvalidArgumentException.class, () -> {
            quadrinhoService.atualizaQuadrinho(1L, 1, "JJK", "NewPop");
        });
        Assertions.assertEquals("Não foi possível alterar o volume, quadrinho já presente", thrown.getMessage());


    }

    @Test
    void atualizaQuadrinhoFailTitulo() {
        Quadrinho quadrinho = new Quadrinho(1L, Raridades.RARO, 3, "JJK", "NewPop", 100);
        when(quadrinhoRepository.findById(1L)).thenReturn(Optional.of(quadrinho));
        when(quadrinhoRepository.findByVolumeTituloEditora(3, "OPM", "NewPop")).thenReturn((Optional.empty()));
        when(quadrinhoRepository.findByVolumeTituloEditora(3, "OPM", "NewPop")).thenReturn((Optional.of(quadrinho)));

        Exception thrown = Assertions.assertThrows(InvalidArgumentException.class, () -> {
            quadrinhoService.atualizaQuadrinho(1L, 3, "OPM", "NewPop");
        });
        Assertions.assertEquals("Não foi possível alterar o título, quadrinho já presente", thrown.getMessage());
    }

    @Test
    void atualizaQuadrinhoFailEditora() {
        Quadrinho quadrinho = new Quadrinho(1L, Raridades.RARO, 3, "JJK", "Panini", 100);
        when(quadrinhoRepository.findById(1L)).thenReturn(Optional.of(quadrinho));
        when(quadrinhoRepository.findByVolumeTituloEditora(3, "JJK", "NewPop")).thenReturn((Optional.empty()));
        when(quadrinhoRepository.findByVolumeTituloEditora(3, "JJK", "NewPop")).thenReturn((Optional.empty()));
        when(quadrinhoRepository.findByVolumeTituloEditora(3, "JJK", "NewPop")).thenReturn((Optional.of(quadrinho)));

        Exception thrown = Assertions.assertThrows(InvalidArgumentException.class, () -> {
            quadrinhoService.atualizaQuadrinho(1L, 3, "JJK", "NewPop");
        });
        Assertions.assertEquals("Não foi possível alterar a editora, quadrinho já presente", thrown.getMessage());
    }

    @Test
    void atualizaRaridade() {
        Quadrinho quadrinho = new Quadrinho(1L, Raridades.RARO, 3, "JJK", "NewPop", 100);
        when(quadrinhoRepository.findById(1L)).thenReturn(Optional.of(quadrinho));

        Quadrinho quadrinhoNew = quadrinhoService.atualizaRaridade(1L, Raridades.COMUM);

        verify(quadrinhoRepository).findById(1L);
        assertThat(quadrinhoNew.getRaridade()).isEqualTo(quadrinho.getRaridade());
    }

    @Test
    void atualizaRaridadeFail() {
        when(quadrinhoRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception thrown = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            quadrinhoService.atualizaRaridade(1L, Raridades.COMUM);
        });
        Assertions.assertEquals("Quadrinho não encontrado", thrown.getMessage());
    }

    @Test
    void atualizaEstoque() {
        Quadrinho quadrinho = new Quadrinho(1L, Raridades.RARO, 3, "JJK", "NewPop", 100);
        int estoqueNew = 10;
        when(quadrinhoRepository.findById(1L)).thenReturn(Optional.of(quadrinho));

        Quadrinho quadrinhoTest = quadrinhoService.atualizaEstoque(1L, estoqueNew);

        assertThat(quadrinhoTest.getEstoque()).isEqualTo(estoqueNew);

    }

    @Test
    void atualizaEstoqueFailQuadrinhoNaoEncontrado() {
        Exception thrown = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            quadrinhoService.atualizaEstoque(1L, 10);
        });
        Assertions.assertEquals("Quadrinho não encontrado", thrown.getMessage());
    }

    @Test
    void atualizaEstoqueFailEstoqueNegativo() {
        Quadrinho quadrinho = new Quadrinho(1L, Raridades.RARO, 3, "JJK", "NewPop", 100);
        int estoqueNew = -10;
        when(quadrinhoRepository.findById(1L)).thenReturn(Optional.of(quadrinho));

        Exception thrown = Assertions.assertThrows(InvalidArgumentException.class, () -> {
            quadrinhoService.atualizaEstoque(1L, estoqueNew);
        });
        Assertions.assertEquals("Estoque não pode ser menor que 0", thrown.getMessage());

    }

    @Test
    void deletarQuadrinho() {
        when(quadrinhoRepository.existsById(1L)).thenReturn(true);

        quadrinhoService.deletarQuadrinho(1L);

        verify(quadrinhoRepository).deleteById(1L);
    }

    @Test
    void deletarQuadrinhoFail() {
        Exception thrown = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            quadrinhoService.deletarQuadrinho(1L);
        });
        Assertions.assertEquals("Quadrinho não encontrado", thrown.getMessage());
    }

}