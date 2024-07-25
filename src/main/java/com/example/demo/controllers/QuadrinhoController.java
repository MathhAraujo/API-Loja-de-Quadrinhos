package com.example.demo.controllers;


import com.example.demo.dto.QuadrinhoDTO;
import com.example.demo.models.Quadrinho;
import com.example.demo.models.Raridades;
import com.example.demo.service.QuadrinhoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "api/quadrinhos")
public class QuadrinhoController {

    private final QuadrinhoService quadrinhoService;

    @Autowired
    public QuadrinhoController(QuadrinhoService quadrinhoService) {
        this.quadrinhoService = quadrinhoService;
    }

    @Operation(description = "Lista todos os quadrinhos na base de dados")
    @GetMapping(path = "/getQuadrinho")
    public ResponseEntity<List<Quadrinho>> getQuadrinhos() {
        List<Quadrinho> lista = quadrinhoService.getQuadrinhos();
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @Operation(description = "Busca quadrinho por id")
    @GetMapping(path = "/getQuadrinho/")
    public ResponseEntity<Quadrinho> getQuadrinhoById(@RequestParam Long quadrinhoId) {
        Quadrinho quadrinho = quadrinhoService.getQuadrinhoById(quadrinhoId);
        return ResponseEntity.status(HttpStatus.OK).body(quadrinho);
    }

    @Operation(description = "Cadastra novo Quadrinho, retorna o quadrinho cadastrado")
    @PostMapping(path = "/cadastrarQuadrinho")
    public ResponseEntity<Quadrinho> cadastraQuadrinho(@RequestBody @Valid QuadrinhoDTO quadrinho) {
        Quadrinho quadrinhoNew = quadrinhoService.cadastraQuadrinho(quadrinho.raridade(), quadrinho.volume(), quadrinho.titulo(), quadrinho.editora());

        return ResponseEntity.status(HttpStatus.CREATED).body(quadrinhoNew);
    }

    @Operation(description = "Atualiza dados de um quadrinho já cadastrado, exceto a raridade")
    @PutMapping(path = "/atualizaQuadrinho")
    public ResponseEntity<Quadrinho> atualizaQuadrinhos(@RequestParam Long quadrinhoId,
                                                        @RequestParam(required = false) Integer volume,
                                                        @RequestParam(required = false) String titulo,
                                                        @RequestParam(required = false) String editora
    ) {
        Quadrinho quadrinho = quadrinhoService.atualizaQuadrinho(quadrinhoId, volume, titulo, editora);
        return ResponseEntity.status(HttpStatus.OK).body(quadrinho);
    }

    @Operation(description = "Atualiza apenas a raridade de um quadrinho já cadastrado")
    @PutMapping(path = "/atualizaRaridade")
    public ResponseEntity<Quadrinho> atualizaRaridade(@RequestParam Long quadrinhoId,
                                                      @RequestParam Raridades raridade) {
        Quadrinho quadrinho = quadrinhoService.atualizaRaridade(quadrinhoId, raridade);
        return ResponseEntity.status(HttpStatus.OK).body(quadrinho);
    }

    @Operation(description = "Atualiza apenas o estoque de um quadrinho já cadastrado")
    @PutMapping(path = "/atualizaEstoque")
    public ResponseEntity<Quadrinho> atualizaEstoque(@RequestParam Long quadrinhoId,
                                                     @RequestParam int estoque) {
        Quadrinho quadrinho = quadrinhoService.atualizaEstoque(quadrinhoId, estoque);
        return ResponseEntity.status(HttpStatus.OK).body(quadrinho);
    }

    @Operation(description = "Deleta um quadrinho cadastrado por id")
    @DeleteMapping(path = "/deletarQuadrinho")
    public ResponseEntity deletarQuadrinho(@RequestParam Long quadrinhoId) {
        quadrinhoService.deletarQuadrinho(quadrinhoId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
