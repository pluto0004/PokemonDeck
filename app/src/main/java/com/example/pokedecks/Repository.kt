package com.example.pokedecks

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class Repository {
    private val TAG = "Repository"
    val pokeName = StringBuffer()

    suspend fun loadPokemon(randomNum: String) = withContext(Dispatchers.IO) {
        try {
            val pokeUrl = URL("https://pokeapi.co/api/v2/pokemon/$randomNum")
            val connection: HttpURLConnection = pokeUrl.openConnection() as HttpURLConnection

            val response = connection.responseCode
            Log.d(TAG, "API: The response code was $response")

            connection.inputStream.buffered().reader().use {
                pokeName.append(it.readText())
                Log.d("CHECK", pokeName.toString())

            }

        } catch (e: Exception) {
            val errorMessage: String = when (e) {
                is MalformedURLException -> "downloadXML: Invalid URL ${e.message}"
                is IOException -> "downloadXML: IO Exception reading data ${e.message}"
                is SecurityException -> {
                    e.printStackTrace()
                    "Security exception. Need permission ${e.message}"

                }
                else -> "Unknown error: ${e.message}"
            }
            Log.e(TAG, errorMessage)
        }
    }

}
