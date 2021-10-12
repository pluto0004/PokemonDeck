package com.example.pokedecks

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.lang.reflect.Type

private const val POKEMON_LIST = "pokemonList"

class PrefConfig {
    fun writeFile(
        context: Context, list: MutableList<PokemonEntity>
    ) {
        try {
            val file = File(context.filesDir, POKEMON_LIST)

            FileWriter(file).use {
                val gson = GsonBuilder().create()
                gson.toJson(list, it)
            }

        } catch (e: IOException) {
            Log.e("writeFile is failed", e.toString())
        }
    }

    fun readFile(context: Context): MutableList<PokemonEntity> {
        val gson = Gson()
        val type: Type = object : TypeToken<MutableList<PokemonEntity>>() {}.type

        return try {
            val file = File(context.filesDir, POKEMON_LIST).bufferedReader().readLine()
            gson.fromJson(file, type)
        } catch (e: IOException) {
            Log.e("readFile is failed", e.toString())
            mutableListOf()
        }
    }
}
