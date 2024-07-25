package com.example.demo.controllers;

import com.example.demo.dto.EncomendaDTO;
import com.example.demo.models.Encomenda;
import com.example.demo.service.EncomendaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/encomendas")
public class EncomendaController {

    private final EncomendaService encomendaService;

    @Autowired
    public EncomendaController(EncomendaService encomendaService) {
        this.encomendaService = encomendaService;
    }

    @Operation(description = "Lista todas as encomendas feitas")
    @GetMapping(path = "/getEncomenda")

    public ResponseEntity<List<Encomenda>> getEncomendas() {
        List<Encomenda> lista = encomendaService.getEncomendas();
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @Operation(description = "Busca encomenda por id ")
    @GetMapping(path = "/getEncomenda/")
    public ResponseEntity<Encomenda> getEncomendaById(@RequestParam Long encomendaId) {
        Encomenda encomenda = encomendaService.getEncomendaById(encomendaId);
        return ResponseEntity.status(HttpStatus.OK).body(encomenda);
    }

    @Operation(description = "Gera nova encomenda, retorna a encomenda gerada")
    @PostMapping(path = "/novaEncomenda")
    public ResponseEntity<Encomenda> novaEncomenda(@RequestBody @Valid EncomendaDTO encomendaDTO,
                                                   @RequestParam(required = false) Long cupomId) {
        Encomenda encomenda = encomendaService.novaEncomenda(encomendaDTO.quantidade(), encomendaDTO.quadrinhoId(), cupomId);
        return ResponseEntity.status(HttpStatus.CREATED).body(encomenda);
    }

    @Operation(description = "Deleta encomenda por id")
    @DeleteMapping(path = "/deletaEncomenda")
    public ResponseEntity deletaEncomenda(@RequestParam Long encomendaId) {
        encomendaService.deletarEncomenda(encomendaId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
