package com.example.pokedecks

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class UseCase(
    private val pokemonList: PokemonRepository = PokemonRepository()
) {

    suspend fun getPokemon(limitOfPokemonsToLoad: Int) = withContext(Dispatchers.IO) {
        val applications = mutableListOf<PokemonEntity>()

        // get the pokemon list from API
        val list =
            JSONObject(pokemonList.loadPokemon(limitOfPokemonsToLoad)).getJSONArray("results")

        // store pokemon name from the API response for the second API call
        for (i in 0 until limitOfPokemonsToLoad) {
            val pokemonEntity = PokemonEntity()
            pokemonEntity.name = list.getJSONObject(i).getString("name")
            pokemonEntity.id = (i + 1).toString()

            setDetails(pokemonEntity)
            applications.add(pokemonEntity)
        }
        applications
    }

    private suspend fun setDetails(pokemon: PokemonEntity) {
        val pokemonDetails = PokemonDetailsRepository()
        val details = JSONObject(pokemonDetails.loadDetails(pokemon.name))

        val length = details.getJSONArray("types").length()
        val typesArr = details.getJSONArray("types")

        pokemon.weight = details.getString("weight")
        pokemon.height = details.getString("height")
        pokemon.image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${
            details.getString("id")
        }.png"

        // Types can be more then 1 type
        for (i in 0 until length) {
            pokemon.type.add(
                typesArr.getJSONObject(i).getJSONObject("type").getString("name")
                    .replaceFirstChar { it.uppercase() })
        }
    }
}
