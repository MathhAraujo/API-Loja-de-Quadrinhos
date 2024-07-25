package com.example.demo.repository;

import com.example.demo.models.Quadrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuadrinhoRepository extends JpaRepository<Quadrinho, Long> {

    @Query("SELECT s FROM Quadrinho s WHERE s.id = ?1")
    Optional<Quadrinho> findById(Long id);

    @Query("SELECT s FROM Quadrinho s WHERE s.volume = ?1 AND s.titulo =?2 AND s.editora=?3")
    Optional<Quadrinho> findByVolumeTituloEditora(int volume, String titulo, String editora);

}
