package com.example.demo.service;

import com.example.demo.Exceptions.InvalidArgumentException;
import com.example.demo.Exceptions.ResourceNotFoundException;
import com.example.demo.models.Cupom;
import com.example.demo.models.Raridades;
import com.example.demo.repository.CupomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class CupomService {

    private final CupomRepository cupomRepository;

    @Autowired
    public CupomService(CupomRepository cupomRepository) {
        this.cupomRepository = cupomRepository;
    }

    public List<Cupom> getCupons() {
        List<Cupom> lista = cupomRepository.findAll();

        if (lista.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum cupom encontrado");
        }

        return lista;
    }

    public Cupom getCupomById(Long cupomId) {
        Optional<Cupom> optionalCupom = cupomRepository.findById(cupomId);

        if (!optionalCupom.isPresent()) {
            throw new ResourceNotFoundException("Cupom não encontrado", cupomId);
        }

        return optionalCupom.get();
    }

    public Cupom gerarCupom() {

        int rng = new Random().nextInt(100);
        Cupom cupom;

        if (rng > 90) {
            cupom = new Cupom(Raridades.RARO);
        } else {
            cupom = new Cupom(Raridades.COMUM);
        }
        cupomRepository.save(cupom);
        return cupom;
    }

    public Cupom gerarCupomCustomizado(Raridades raridade, LocalDate validade) {
        if (validade.isBefore(LocalDate.now())) {
            throw new InvalidArgumentException("Validade inválida", validade);
        }

        Cupom cupomNew = new Cupom(raridade, validade);
        cupomRepository.save(cupomNew);

        return cupomNew;
    }

    public void deletarCupom(Long cupomId) {
        boolean exists = cupomRepository.existsById(cupomId);
        if (!exists) {
            throw new ResourceNotFoundException("Cupom não encontrado", cupomId);
        }
        cupomRepository.deleteById(cupomId);
    }
}
