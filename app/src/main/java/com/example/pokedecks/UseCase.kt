package com.example.pokedecks

import android.util.Log
import org.json.JSONObject

class UseCase {
    private val TAG = "UseCase"
    suspend fun generatePokemonDetails(randomNum: String, tag: String): String {
        val repository = Repository()
        val pokemonEntity = PokemonEntity()

        val pokemon = JSONObject(repository.loadPokemon(randomNum))

        pokemonEntity.id = pokemon.getString("id")
        pokemonEntity.name = pokemon.getString("name").uppercase()
        pokemonEntity.weight = pokemon.getString("weight")

        Log.d(TAG, pokemonEntity.id)

        return when (tag) {
            "id" -> pokemonEntity.id
            "name" -> pokemonEntity.name
            "weight" -> pokemonEntity.weight
            else -> ""
        }
    }
}
