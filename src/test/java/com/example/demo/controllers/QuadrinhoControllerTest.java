package com.example.demo.controllers;

import com.example.demo.dto.QuadrinhoDTO;
import com.example.demo.filter.SecurityFilter;
import com.example.demo.models.Quadrinho;
import com.example.demo.models.Raridades;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.QuadrinhoService;
import com.example.demo.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = QuadrinhoController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class QuadrinhoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private QuadrinhoService quadrinhoService;
    @MockBean
    private TokenService tokenService;
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private SecurityFilter securityFilter;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getQuadrinhos() throws Exception {
        Quadrinho quadrinho = new Quadrinho(1L, Raridades.COMUM, 10, "OPM", "Panini", 0);

        when(quadrinhoService.getQuadrinhos()).thenReturn(List.of(quadrinho));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/quadrinhos/getQuadrinho")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(quadrinho.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].raridade", Matchers.is(quadrinho.getRaridade().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].volume", Matchers.is(quadrinho.getVolume())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].titulo", Matchers.is(quadrinho.getTitulo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].editora", Matchers.is(quadrinho.getEditora())));

    }

    @Test
    void getQuadrinhoById() throws Exception {
        Quadrinho quadrinho = new Quadrinho(1L, Raridades.COMUM, 10, "OPM", "Panini", 0);

        when(quadrinhoService.getQuadrinhoById(anyLong())).thenReturn(quadrinho);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/quadrinhos/getQuadrinho/")
                        .contentType(MediaType.APPLICATION_JSON).param("quadrinhoId", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(quadrinho.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.raridade", Matchers.is(quadrinho.getRaridade().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.volume", Matchers.is(quadrinho.getVolume())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.titulo", Matchers.is(quadrinho.getTitulo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.editora", Matchers.is(quadrinho.getEditora())));
    }

    @Test
    void cadastraQuadrinho() throws Exception {
        QuadrinhoDTO quadrinhoDTO = new QuadrinhoDTO(Raridades.COMUM, 10, "OPM", "Panini");
        Quadrinho quadrinho = new Quadrinho(1L, Raridades.COMUM, 10, "OPM", "Panini", 0);

        when(quadrinhoService.cadastraQuadrinho(quadrinhoDTO.raridade(),
                quadrinhoDTO.volume(),
                quadrinhoDTO.titulo(),
                quadrinhoDTO.editora()))
                .thenReturn(quadrinho);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/quadrinhos/cadastrarQuadrinho")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(quadrinhoDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(quadrinho.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.raridade", Matchers.is(quadrinho.getRaridade().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.volume", Matchers.is(quadrinho.getVolume())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.titulo", Matchers.is(quadrinho.getTitulo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.editora", Matchers.is(quadrinho.getEditora())));

    }

    @Test
    void atualizaQuadrinhos() throws Exception {
        Quadrinho quadrinho = new Quadrinho(1L, Raridades.COMUM, 10, "OPM", "Panini", 0);

        when(quadrinhoService.atualizaQuadrinho(1L, 10, "OPM", "Panini")).thenReturn(quadrinho);

        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/api/quadrinhos/atualizaQuadrinho")
                        .contentType(MediaType.APPLICATION_JSON).param("quadrinhoId", "1")
                        .param("volume", "10")
                        .param("titulo", "OPM")
                        .param("editora", "Panini"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(quadrinho.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.volume", Matchers.is(quadrinho.getVolume())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.titulo", Matchers.is(quadrinho.getTitulo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.editora", Matchers.is(quadrinho.getEditora())));
    }

    @Test
    void atualizaRaridade() throws Exception {
        Quadrinho quadrinho = new Quadrinho(1L, Raridades.RARO, 10, "OPM", "Panini", 0);

        when(quadrinhoService.atualizaRaridade(1L, Raridades.RARO)).thenReturn(quadrinho);

        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/api/quadrinhos/atualizaRaridade")
                        .contentType(MediaType.APPLICATION_JSON).param("quadrinhoId", "1").param("raridade", "RARO"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(quadrinho.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.raridade", Matchers.is(quadrinho.getRaridade().toString())));
    }

    @Test
    void atualizaEstoque() throws Exception {
        Quadrinho quadrinho = new Quadrinho(1L, Raridades.RARO, 15, "OPM", "Panini", 10);

        when(quadrinhoService.atualizaEstoque(1L, 10)).thenReturn(quadrinho);

        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/api/quadrinhos/atualizaEstoque")
                        .contentType(MediaType.APPLICATION_JSON).param("quadrinhoId", "1").param("estoque", "10"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(quadrinho.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.estoque", Matchers.is(quadrinho.getEstoque())));

    }

    @Test
    void deletarQuadrinho() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/quadrinhos/deletarQuadrinho").param("quadrinhoId", "1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}