package com.exo.pokemon.controller;


import com.exo.pokemon.entities.Pokemon;
import com.exo.pokemon.repository.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PokemonController {


    @Autowired
    PokemonRepository pokemonRepository;



    @PostMapping("/pokemon")
    public Pokemon create(@RequestBody Pokemon pokemon){
        return pokemonRepository.save(pokemon);
    }


    @GetMapping("/pokemons")
    public List<Pokemon> readAll(){
        return pokemonRepository.findAll();
    }


    @GetMapping("/pokemon/{name}")
    public Pokemon readByName(@PathVariable String name){
        return pokemonRepository.findByName(name);
    }



    @PutMapping("/pokemons/{name}")
    public Pokemon update(@PathVariable String name,@RequestBody Pokemon newName){

        Pokemon pokemonToUpdate = pokemonRepository.findByName(name);
        pokemonToUpdate.setName(newName.getName());

        return pokemonRepository.save(pokemonToUpdate);
    }


    @DeleteMapping("pokemon/{id}")
    public boolean delete(@PathVariable Long id){
        pokemonRepository.deleteById(id);
        return true;
    }
}
