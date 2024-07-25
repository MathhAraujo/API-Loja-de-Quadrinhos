package com.example.demo.repository;

import com.example.demo.models.Encomenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EncomendaRepository extends JpaRepository<Encomenda, Long> {
    @Query("SELECT s FROM Encomendas s WHERE s.id = ?1")
    Optional<Encomenda> findById(Long id);
}
