package com.example.pokedecks

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

private const val LIST_KEY = "LIST_KEY"

// TODO: Is the way of using preference correct?? is there any other way?
class PrefConfig {

    fun writeListInPref(
        context: Context, list: MutableList<PokemonEntity>
    ) {
        val gson = Gson()
        val jsonString = gson.toJson(list)

        val pref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

        pref.edit().putString(LIST_KEY, jsonString).apply()
    }

    fun readListFromPref(context: Context): MutableList<PokemonEntity>? {
        val pref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val jsonString = pref.getString(LIST_KEY, "")
        return if (jsonString == null) {
            null
        } else {
            val gson = Gson()
            val type: Type = object : TypeToken<MutableList<PokemonEntity>>() {}.type

            gson.fromJson<MutableList<PokemonEntity>?>(jsonString, type)
        }
    }
}
