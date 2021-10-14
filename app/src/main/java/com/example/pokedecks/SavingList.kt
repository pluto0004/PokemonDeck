package com.example.pokedecks

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.lang.reflect.Type

private const val TAG = "SavingList"

class SavingList {

    fun writeFile(
        context: Context,
        list: List<PokemonEntity>,
        fileName: String
    ) {
        try {
            Log.d(TAG, "writeFile called")

            val file = File(context.filesDir, fileName)

            FileWriter(file).use {
                val gson = GsonBuilder().create()
                gson.toJson(list, it)
            }
        } catch (e: IOException) {
            Log.e("writeFile is failed", e.toString())
        }
    }

    fun readFile(context: Context, fileName: String): List<PokemonEntity> {
        val gson = Gson()
        val type: Type = object : TypeToken<List<PokemonEntity>>() {}.type


        try {
            val file = File(context.filesDir, fileName).bufferedReader().readLine()
            return gson.fromJson(file, type)

        } catch (e: JsonSyntaxException) {
            Log.e(TAG, "JsonSyntaxException", e)
        } catch (e: IllegalStateException) {
            Log.e(TAG, "IllegalStateException", e)
        } catch (e: IOException) {
            Log.e(TAG, "IOException", e)
        }
        return listOf()
        // return an empty list if there was an exception
    }
}

