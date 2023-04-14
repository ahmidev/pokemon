package com.exo.pokemon.controller;


import com.exo.pokemon.entities.Pokemon;
import com.exo.pokemon.entities.Veterinaire;
import com.exo.pokemon.repository.PokemonRepository;
import com.exo.pokemon.repository.VeterinaireRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




import java.util.List;

@RestController
public class PokemonController {


    @Autowired
    PokemonRepository pokemonRepository;

    @Autowired
    private VeterinaireRepository veterinaireRepository;




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
        return pokemonRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Pokémon introuvable"));
    }



    @PutMapping("/pokemons/{name}")
    public Pokemon update(@PathVariable String name,@RequestBody Pokemon newName){

        Pokemon pokemonToUpdate = pokemonRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Pokémon introuvable"));
        pokemonToUpdate.setName(newName.getName());

        return pokemonRepository.save(pokemonToUpdate);
    }


    @DeleteMapping("pokemon/{id}")
    public boolean delete(@PathVariable Long id){
        pokemonRepository.deleteById(id);
        return true;
    }

    @DeleteMapping("/pokemon2/{name}")
    public ResponseEntity<Void> deletePokemonByName(@PathVariable String name) {
        Pokemon pokemon = pokemonRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Pokémon introuvable"));

        //Supprimer la relation avec le vétérinaire
        if (pokemon.getVeterinaire() != null) {
            Veterinaire veterinaire = pokemon.getVeterinaire();
            veterinaire.getPokemons().remove(pokemon);
            pokemon.setVeterinaire(null);
            veterinaireRepository.save(veterinaire);
        }

        // on supprime le pokemon
        pokemonRepository.delete(pokemon);

        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/veterinaire/{name}")
    public ResponseEntity<Void> deleteVeterinaireByName(@PathVariable String name) {
        Veterinaire veterinaire = veterinaireRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Vétérinaire introuvable"));

        // Supprimer la relation avec les pokémons
        for (Pokemon pokemon : veterinaire.getPokemons()) {
            pokemon.setVeterinaire(null);
            pokemonRepository.save(pokemon);
        }

        // Supprimer le vétérinaire
        veterinaireRepository.delete(veterinaire);

        return ResponseEntity.noContent().build();
    }



}
