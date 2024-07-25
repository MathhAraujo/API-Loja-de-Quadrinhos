package com.example.demo.controllers;

import com.example.demo.dto.CupomDTO;
import com.example.demo.filter.SecurityFilter;
import com.example.demo.models.Cupom;
import com.example.demo.models.Raridades;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CupomService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = CupomController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class CupomControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CupomService cupomService;
    @MockBean
    private TokenService tokenService;
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private SecurityFilter securityFilter;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getCupons() throws Exception {
        Cupom cupom = new Cupom(1L, Raridades.RARO, LocalDate.now().plusYears(2));
        when(cupomService.getCupons()).thenReturn(List.of(cupom));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/cupons/getCupom")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(cupom.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].raridade", Matchers.is(cupom.getRaridade().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].validade", Matchers.is(cupom.getValidade().toString())));
    }

    @Test
    void getCupomById() throws Exception {
        Cupom cupom = new Cupom(1L, Raridades.RARO, LocalDate.now().plusYears(2));
        when(cupomService.getCupomById(any())).thenReturn(cupom);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/cupons/getCupom/")
                        .contentType(MediaType.APPLICATION_JSON).param("cupomId", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(cupom.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.raridade", Matchers.is(cupom.getRaridade().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validade", Matchers.is(cupom.getValidade().toString())));
    }

    @Test
    void gerarCupom() throws Exception {
        Cupom cupom = new Cupom(1L, Raridades.RARO, LocalDate.now().plusYears(2));
        when(cupomService.gerarCupom()).thenReturn(cupom);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/cupons/gerarCupom")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(cupom.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.raridade", Matchers.is(cupom.getRaridade().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validade", Matchers.is(cupom.getValidade().toString())));
    }

    @Test
    void gerarCupomCustomizado() throws Exception {
        Cupom cupom = new Cupom(1L, Raridades.RARO, LocalDate.now().plusYears(2));
        CupomDTO cupomDTO = new CupomDTO(Raridades.RARO, LocalDate.now().plusYears(2));
        when(cupomService.gerarCupomCustomizado(cupomDTO.raridade(), cupomDTO.validade())).thenReturn(cupom);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/cupons/gerarCupomCustomizado")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(cupomDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.raridade", Matchers.is(cupom.getRaridade().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validade", Matchers.is(cupom.getValidade().toString())));
    }

    @Test
    void deletarCupom() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/cupons/deletarCupom").param("cupomId", "1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}