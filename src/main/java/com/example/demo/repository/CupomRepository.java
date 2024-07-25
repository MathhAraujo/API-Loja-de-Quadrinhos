package com.example.demo.repository;

import com.example.demo.models.Cupom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CupomRepository extends JpaRepository<Cupom, Long> {

    @Query("SELECT s FROM Cupom s WHERE s.id = ?1")
    Optional<Cupom> findById(Long id);

}
