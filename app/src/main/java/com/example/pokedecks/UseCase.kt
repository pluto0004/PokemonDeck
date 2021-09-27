package com.example.pokedecks

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
            pokemonEntity.name = list.getJSONObject(i).getString("name")
            pokemonEntity.id = (i + 1).toString()
            applications.add(pokemonEntity)
        }

        return applications
    }

    suspend fun setDetails(name: String) {
        val pokemonDetails = PokemonDetails()
        val details = JSONObject(pokemonDetails.loadDetails(name))

        for(pokemon in applications){
            var length =details.getJSONArray("types").length()
            var typesArr = details.getJSONArray("types")

            if(pokemon.name == name){
                pokemon.weight = details.getString("weight")
                pokemon.height = details.getString("height")
                pokemon.image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${details.getString("id")}.png"

                // Types can be more then 1 type
                for(i in 0 until length){
                    pokemon.type.add(typesArr.getJSONObject(i).getJSONObject("type").getString("name"))
                }
            }
        }
    }




}
