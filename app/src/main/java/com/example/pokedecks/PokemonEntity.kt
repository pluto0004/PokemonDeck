package com.example.pokedecks

class PokemonEntity {
    var id: String = ""
    var name: String = ""
    var weight: String = ""
    var height: String = ""
    var image: String = ""
    val type: ArrayList<String> = arrayListOf()

    override fun toString(): String {
        return """
            id = $id
            name = $name
            weight = $weight
            height = $height
            image = $image
            type = $type
        """.trimIndent()
    }
}
