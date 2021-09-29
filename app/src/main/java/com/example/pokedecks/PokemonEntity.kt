package com.example.pokedecks

//toString
//Equals & HasCode
data class PokemonEntity(
    var id: String = "",
    var name: String = "",
    var weight: String = "",
    var height: String = "",
    var image: String = "",
    val type: ArrayList<String> = arrayListOf()
)
