package com.example.pokedecks

import org.json.JSONObject


class UseCase {
    fun postExecute(pokemon: String, tag: String): String {
        val parentJsonObj = JSONObject(pokemon)

        return when (tag) {
            "id" -> parentJsonObj.getString("id")
            "name" -> parentJsonObj.getString("name").uppercase()
            "weight" -> parentJsonObj.getString("weight")
            else -> ""
        }
    }
}
