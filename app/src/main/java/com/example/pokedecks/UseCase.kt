package com.example.pokedecks

import android.util.Log
import org.json.JSONObject

class UseCase {
    private val TAG = "UseCase"
    val pokemonEntity = PokemonEntity()

    suspend fun getPokemon(randomNum: String) {
        val repository = Repository()
        val pokemon = JSONObject(repository.loadPokemon(randomNum))

        pokemonEntity.id = pokemon.getString("id")
        pokemonEntity.name = pokemon.getString("name").uppercase()
        pokemonEntity.weight = pokemon.getString("weight")

        Log.d(TAG, pokemonEntity.id)
    }

    fun generateDetails(tag: String): String{
        return when (tag) {
            "id" -> pokemonEntity.id
            "name" -> pokemonEntity.name
            "weight" -> pokemonEntity.weight
            else -> ""
        }
    }
}
