package com.example.demo.controllers;

import com.example.demo.dto.CupomDTO;
import com.example.demo.models.Cupom;
import com.example.demo.service.CupomService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/cupons")
public class CupomController {

    private final CupomService cupomService;

    @Autowired
    public CupomController(CupomService cupomService) {
        this.cupomService = cupomService;
    }

    @Operation(description = "Lista todos os cupons disponíveis")
    @GetMapping(path = "/getCupom")
    public ResponseEntity<List<Cupom>> getCupons() {
        List<Cupom> lista = cupomService.getCupons();
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @Operation(description = "Busca cupom por id")
    @GetMapping(path = "/getCupom/")
    public ResponseEntity<Cupom> getCupomById(@RequestParam Long cupomId) {
        Cupom cupom = cupomService.getCupomById(cupomId);
        return ResponseEntity.status(HttpStatus.OK).body(cupom);
    }

    @Operation(summary = "Gera cupom aleatório",
            description = "Gera cupom de raridade aleatória e validade entre 1 e 12 meses. Retorna o cupom gerado")
    @GetMapping(path = "/gerarCupom")
    public ResponseEntity<Cupom> gerarCupom() {
        Cupom cupom = cupomService.gerarCupom();
        return ResponseEntity.status(HttpStatus.CREATED).body(cupom);
    }

    @Operation(description = "Gera cupom com raridade e validade customizáveis, retorna o cupom gerado")
    @PostMapping(path = "/gerarCupomCustomizado")
    public ResponseEntity<Cupom> gerarCupomCustomizado(@RequestBody @Valid CupomDTO cupom) {
        Cupom cupomNew = cupomService.gerarCupomCustomizado(cupom.raridade(), cupom.validade());
        return ResponseEntity.status(HttpStatus.CREATED).body(cupomNew);
    }

    @Operation(description = "Deleta cupom por id")
    @DeleteMapping(path = "/deletarCupom")
    public ResponseEntity deletarCupom(@RequestParam Long cupomId) {
        cupomService.deletarCupom(cupomId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
