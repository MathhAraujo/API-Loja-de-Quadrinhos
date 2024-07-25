package com.example.demo.controllers;

import com.example.demo.dto.EncomendaDTO;
import com.example.demo.filter.SecurityFilter;
import com.example.demo.models.Encomenda;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.EncomendaService;
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

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(controllers = EncomendaController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class EncomendaControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EncomendaService encomendaService;
    @MockBean
    private TokenService tokenService;
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private SecurityFilter securityFilter;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getEncomendas() throws Exception {
        Encomenda encomenda = new Encomenda(1L, LocalDate.now(), 15, 1L, 10L);

        when(encomendaService.getEncomendas()).thenReturn(List.of(encomenda));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/encomendas/getEncomenda")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(encomenda.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dataDoPedido", Matchers.is(encomenda.getDataDoPedido().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].quantidade", Matchers.is(encomenda.getQuantidade())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].quadrinhoId", Matchers.is(encomenda.getQuadrinhoId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cupomId", Matchers.is(encomenda.getCupomId().intValue())));
    }

    @Test
    void getEncomendaById() throws Exception {
        Encomenda encomenda = new Encomenda(1L, LocalDate.now(), 15, 1L, 10L);

        when(encomendaService.getEncomendaById(1L)).thenReturn(encomenda);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/encomendas/getEncomenda/")
                        .contentType(MediaType.APPLICATION_JSON).param("encomendaId", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(encomenda.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataDoPedido", Matchers.is(encomenda.getDataDoPedido().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantidade", Matchers.is(encomenda.getQuantidade())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quadrinhoId", Matchers.is(encomenda.getQuadrinhoId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cupomId", Matchers.is(encomenda.getCupomId().intValue())));

    }

    @Test
    void novaEncomenda() throws Exception {

        Long cupomId = 10L;
        EncomendaDTO encomendaDTO = new EncomendaDTO(15, 1L);
        Encomenda encomenda = new Encomenda(1L, LocalDate.now(), 15, 1L, 10L);

        when(encomendaService.novaEncomenda(encomendaDTO.quantidade(), encomendaDTO.quadrinhoId(), cupomId)).thenReturn(encomenda);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/encomendas/novaEncomenda")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(encomendaDTO)).param("cupomId", "10"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantidade", Matchers.is(encomenda.getQuantidade())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quadrinhoId", Matchers.is(encomenda.getQuadrinhoId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cupomId", Matchers.is(encomenda.getCupomId().intValue())));

    }

    @Test
    void deletaEncomenda() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/encomendas/deletaEncomenda").param("encomendaId", "1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}