package com.example.demo.service;

import com.example.demo.Exceptions.InvalidArgumentException;
import com.example.demo.Exceptions.ResourceNotFoundException;
import com.example.demo.models.Cupom;
import com.example.demo.models.Encomenda;
import com.example.demo.models.Quadrinho;
import com.example.demo.repository.CupomRepository;
import com.example.demo.repository.EncomendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EncomendaService {

    private final EncomendaRepository encomendaRepository;
    private final CupomService cupomService;
    private final QuadrinhoService quadrinhoService;
    private final CupomRepository cupomRepository;

    @Autowired
    public EncomendaService(EncomendaRepository encomendaRepository, CupomService cupomService, QuadrinhoService quadrinhoService, CupomRepository cupomRepository) {
        this.encomendaRepository = encomendaRepository;
        this.cupomService = cupomService;
        this.quadrinhoService = quadrinhoService;
        this.cupomRepository = cupomRepository;
    }

    public List<Encomenda> getEncomendas() {
        List<Encomenda> lista = encomendaRepository.findAll();
        if (lista.isEmpty()) {
            throw new ResourceNotFoundException("Nenhuma encomenda encontrada");
        }
        return lista;
    }

    public Encomenda getEncomendaById(Long encomendaId) {
        Optional<Encomenda> optionalEncomenda = encomendaRepository.findById(encomendaId);

        if (optionalEncomenda.isEmpty()) {
            throw new ResourceNotFoundException("Encomenda não encontrada", encomendaId);
        }
        return optionalEncomenda.get();
    }

    public Encomenda novaEncomenda(int quantidade, Long quadrinhoId, Long cupomId) {

        Quadrinho quadrinho = quadrinhoService.getQuadrinhoById(quadrinhoId);
        Encomenda encomenda;

        if (quadrinho.getEstoque() >= quantidade) {

            if (cupomId != null) {

                Cupom cupom = cupomService.getCupomById(cupomId);

                if (cupom.getRaridade() == quadrinho.getRaridade()) {

                    encomenda = new Encomenda(quantidade, quadrinhoId, cupomId);
                    cupomService.deletarCupom(cupomId);

                } else {
                    throw new InvalidArgumentException(HttpStatus.BAD_REQUEST, "Quadrinho e cupom possuem raridades diferentes", cupom);
                }

            } else {
                encomenda = new Encomenda(quantidade, quadrinhoId);
            }


            encomendaRepository.save(encomenda);
            quadrinhoService.atualizaEstoque(quadrinhoId, quadrinho.getEstoque() - quantidade);

            return encomenda;

        } else if (quadrinho.getEstoque() == 0) {
            throw new InvalidArgumentException(HttpStatus.INTERNAL_SERVER_ERROR, "Quadrinho fora de estoque", quantidade);
        } else {
            throw new InvalidArgumentException(HttpStatus.INTERNAL_SERVER_ERROR, "Pedido maior que estoque disponível", quantidade);
        }
    }

    public void deletarEncomenda(Long encomendaId) {
        boolean exists = encomendaRepository.existsById(encomendaId);
        if (!exists) {
            throw new ResourceNotFoundException("Encomenda não encontrada", encomendaId);
        }
        encomendaRepository.deleteById(encomendaId);
    }

}