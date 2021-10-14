package com.example.pokedecks

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

private const val TAG = "PokemonViewModel"
val EMPTY_POKEMON_LIST: List<PokemonEntity> = Collections.emptyList()

class PokemonViewModel : ViewModel() {
    private val useCase = UseCase()


    private val pokemon = MutableLiveData<List<PokemonEntity>>()
    val pokemonList: LiveData<List<PokemonEntity>>
        get() = pokemon

//    private val network = MutableLiveData<Boolean>()
//    val isOnline: LiveData<Boolean>
//        get() = network


    suspend fun downloadPokemonList(limitNum: Int) {
        Log.d(TAG, "downloadPokemonList called")
        val pokemonList = useCase.getPokemon(limitNum)
        pokemon.postValue(pokemonList)

    }


    fun searchQuery(masterList: List<PokemonEntity>, query: String) {
        pokemon.value =
            masterList.filter { it -> it.type.contains(query.replaceFirstChar { it.uppercase() }) } as MutableList<PokemonEntity>
    }

}
