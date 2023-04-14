package com.exo.pokemon.repository;

import com.exo.pokemon.entities.Veterinaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VeterinaireRepository extends JpaRepository<Veterinaire,Long> {

    Optional<Veterinaire> findByName(String name);
}
