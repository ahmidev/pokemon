package com.exo.pokemon.repository;

import com.exo.pokemon.entities.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PokemonRepository  extends JpaRepository<Pokemon, Long> {

   Optional<Pokemon> findByName(String name);
}
