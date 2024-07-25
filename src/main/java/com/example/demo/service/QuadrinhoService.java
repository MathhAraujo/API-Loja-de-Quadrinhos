package com.example.demo.service;

import com.example.demo.Exceptions.InvalidArgumentException;
import com.example.demo.Exceptions.ResourceNotFoundException;
import com.example.demo.models.Quadrinho;
import com.example.demo.models.Raridades;
import com.example.demo.repository.QuadrinhoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuadrinhoService {
    private final QuadrinhoRepository quadrinhoRepository;

    @Autowired
    public QuadrinhoService(QuadrinhoRepository quadrinhoRepository) {
        this.quadrinhoRepository = quadrinhoRepository;
    }

    public List<Quadrinho> getQuadrinhos() {
        List lista = quadrinhoRepository.findAll();
        if (lista.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum quadrinho encontrado");
        }
        return lista;
    }

    public Quadrinho getQuadrinhoById(Long quadrinhoId) {
        Optional<Quadrinho> quadrinho = quadrinhoRepository.findById(quadrinhoId);

        if (!quadrinho.isPresent()) {
            throw new ResourceNotFoundException("Quadrinho não encontrado", quadrinhoId);
        } else {
            return quadrinho.get();
        }
    }

    public Quadrinho cadastraQuadrinho(Raridades raridade, int volume, String titulo, String editora) {

        Optional<Quadrinho> quadrinhoOptional = quadrinhoRepository.findByVolumeTituloEditora(volume, titulo, editora);

        if (quadrinhoOptional.isPresent()) {
            throw new InvalidArgumentException(HttpStatus.CONFLICT, "Quadrinho já cadastrado", quadrinhoOptional.get());
        }

        Quadrinho quadrinhoNew = new Quadrinho(raridade, volume, titulo, editora);
        quadrinhoRepository.save(quadrinhoNew);

        return quadrinhoNew;

    }

    @Transactional
    public Quadrinho atualizaQuadrinho(Long quadrinhoId, Integer volume, String titulo, String editora) {

        Optional<Quadrinho> quadrinhoOptional = quadrinhoRepository.findById(quadrinhoId);

        if (!quadrinhoOptional.isPresent()) {
            throw new ResourceNotFoundException("Quadrinho não encontrado", quadrinhoId);
        }

        Quadrinho quadrinho = quadrinhoOptional.get();

        if (volume != null) {

            if (quadrinhoRepository.findByVolumeTituloEditora(volume, quadrinho.getTitulo(), quadrinho.getEditora()).isPresent()) {
                throw new InvalidArgumentException(HttpStatus.CONFLICT, "Não foi possível alterar o volume, quadrinho já presente", volume);
            }
            quadrinho.setVolume(volume);
        }


        if (titulo != null && titulo.length() > 0) {

            if (quadrinhoRepository.findByVolumeTituloEditora(quadrinho.getVolume(), titulo, quadrinho.getEditora()).isPresent()) {
                throw new InvalidArgumentException(HttpStatus.CONFLICT, "Não foi possível alterar o título, quadrinho já presente", titulo);
            }
            quadrinho.setTitulo(titulo);
        }


        if (editora != null && editora.length() > 0) {

            if (quadrinhoRepository.findByVolumeTituloEditora(quadrinho.getVolume(), quadrinho.getTitulo(), editora).isPresent()) {
                throw new InvalidArgumentException(HttpStatus.CONFLICT, "Não foi possível alterar a editora, quadrinho já presente", editora);
            }
            quadrinho.setEditora(editora);
        }

        return quadrinho;

    }

    @Transactional
    public Quadrinho atualizaRaridade(Long quadrinhoId, Raridades raridade) {

        Optional<Quadrinho> quadrinhoOptional = quadrinhoRepository.findById(quadrinhoId);

        if (!quadrinhoOptional.isPresent()) {
            throw new ResourceNotFoundException("Quadrinho não encontrado", quadrinhoId);
        }

        Quadrinho quadrinho = quadrinhoOptional.get();
        quadrinho.setRaridade(raridade);

        return quadrinho;

    }

    @Transactional
    public Quadrinho atualizaEstoque(Long quadrinhoId, int estoque) {

        Optional<Quadrinho> quadrinhoOptional = quadrinhoRepository.findById(quadrinhoId);

        if (!quadrinhoOptional.isPresent()) {
            throw new ResourceNotFoundException("Quadrinho não encontrado", quadrinhoId);
        }

        Quadrinho quadrinho = quadrinhoOptional.get();

        if (estoque >= 0) {
            quadrinho.setEstoque(estoque);
        } else {
            throw new InvalidArgumentException("Estoque não pode ser menor que 0", estoque);
        }

        return quadrinho;

    }

    public void deletarQuadrinho(Long quadrinhoId) {
        boolean exists = quadrinhoRepository.existsById(quadrinhoId);
        if (!exists) {
            throw new ResourceNotFoundException("Quadrinho não encontrado", quadrinhoId);
        }
        quadrinhoRepository.deleteById(quadrinhoId);
    }

}
