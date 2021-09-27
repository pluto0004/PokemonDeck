package com.example.pokedecks

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class PokemonDetails {
    private val TAG = "PokemonDetails"

    suspend fun loadDetails(name:String)= withContext(Dispatchers.IO) {
        val details = StringBuffer()
        Log.d(TAG, "API: name $name")

        try {
            val pokeUrl = URL("https://pokeapi.co/api/v2/pokemon/$name")
            val connection: HttpURLConnection = pokeUrl.openConnection() as HttpURLConnection

            val response = connection.responseCode
            Log.d(TAG, "API: The response code was $response")

            connection.inputStream.buffered().reader().use {
                details.append(it.readText())
                Log.d(TAG, details.toString())
            }

            connection.disconnect()

        } catch (e: Exception) {
            val errorMessage: String = when (e) {
                is MalformedURLException -> "getDetail: Invalid URL ${e.message}"
                is IOException -> "getDetail: IO Exception reading data ${e.message}"
                is SecurityException -> {
                    e.printStackTrace()
                    "Security exception. Need permission ${e.message}"

                }
                else -> "Unknown error: ${e.message}"
            }
            Log.e(TAG, errorMessage)
        }
        details.toString()
    }
    }
