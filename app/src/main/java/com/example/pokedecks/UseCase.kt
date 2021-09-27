package com.example.pokedecks

import android.util.Log
import org.json.JSONObject

class UseCase {
    private val TAG = "UseCase"
    val applications = ArrayList<PokemonEntity>()

    suspend fun getPokemon(limit: Int):ArrayList<PokemonEntity> {
        val pokemonList = PokemonList()

        // get the pokemon list from API
        val list = JSONObject(pokemonList.loadPokemon(limit)).getJSONArray("results")

        // store pokemon name from the API response for the second API call
        for(i in 0 until limit){
            val pokemonEntity = PokemonEntity()
            Log.d(TAG, i.toString())
            pokemonEntity.name = list.getJSONObject(i).getString("name")
            pokemonEntity.id = (i + 1).toString()
            applications.add(pokemonEntity)
        }
        Log.d(TAG, applications.toString())

        return applications
    }

    suspend fun setDetails(name: String) {
        val pokemonDetails = PokemonDetails()
        val details = JSONObject(pokemonDetails.loadDetails(name))

        for(pokemon in applications){
            if(pokemon.name == name){
                pokemon.weight = details.getString("weight")
                pokemon.height = details.getString("height")
                pokemon.image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${details.getString("id")}.png"
                Log.d("getDetails", pokemon.toString())
            }
                    }
    }


}
