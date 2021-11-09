package com.example.pokedecks

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokedecks.utils.NetworkUtils
import java.util.*

private const val TAG = "PokemonViewModel"
val EMPTY_POKEMON_LIST: List<PokemonEntity> = Collections.emptyList()

class PokemonViewModel : ViewModel() {
    val limitOfPokemonsToLoad = 5

    private val useCase = UseCase()
    private val networkUtils = NetworkUtils()
    private val savingList = SavingList()

    private val pokemon = MutableLiveData<List<PokemonEntity>>()
    val pokemonList: LiveData<List<PokemonEntity>>
        get() = pokemon


    suspend fun downloadPokemonList(limitNum: Int) {
        Log.d(TAG, "downloadPokemonList called")
        val pokemonList = useCase.getPokemon(limitNum)
        pokemon.postValue(pokemonList)
    }

    fun searchQuery(masterList: List<PokemonEntity>, query: String) {
        pokemon.value =
            masterList.filter { it -> it.type.contains(query.replaceFirstChar { it.uppercase() }) } as MutableList<PokemonEntity>
    }

    fun onlineCheck(context: Context): Boolean {
        return networkUtils.isOnline(context)
    }

    fun readFile(context: Context, filename: String): List<PokemonEntity> {
        return savingList.readFile(context, filename)
    }

    fun writeFile(context: Context, list: List<PokemonEntity>, filename: String) {
        savingList.writeFile(context, list, filename)
    }

}
