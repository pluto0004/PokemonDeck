package com.example.pokedecks

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class PokemonList {
    private val TAG = "PokemonList"

    suspend fun loadPokemon(limit:Int) = withContext(Dispatchers.IO) {
        val pokemonList = StringBuffer()
        try {
            val pokeUrl = URL("https://pokeapi.co/api/v2/pokemon/?limit=${limit}")
            val connection: HttpURLConnection = pokeUrl.openConnection() as HttpURLConnection

            val response = connection.responseCode
            Log.d(TAG, "API: The response code was $response")

            connection.inputStream.buffered().reader().use {
                pokemonList.append(it.readText())
                Log.d(TAG, pokemonList.toString())
            }

            connection.disconnect()

        } catch (e: Exception) {
            val errorMessage: String = when (e) {
                is MalformedURLException -> "loadPokemon: Invalid URL ${e.message}"
                is IOException -> "loadPokemon: IO Exception reading data ${e.message}"
                is SecurityException -> {
                    e.printStackTrace()
                    "Security exception. Need permission ${e.message}"

                }
                else -> "Unknown error: ${e.message}"
            }
            Log.e(TAG, errorMessage)
        }
        pokemonList.toString()
    }
}
