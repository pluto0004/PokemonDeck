package com.example.pokedecks

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//toString
//Equals & HasCode
@Parcelize
data class PokemonEntity(
    var id: String = "",
    var name: String = "",
    var weight: String = "",
    var height: String = "",
    var image: String = "",
    val type: ArrayList<String> = arrayListOf()
) : Parcelable
