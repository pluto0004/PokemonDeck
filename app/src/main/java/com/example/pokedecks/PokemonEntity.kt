package com.example.pokedecks

class PokemonEntity {
    var id: String = ""
    var name: String = ""
    var weight: String = ""

    override fun toString(): String {
        return """
            id = $id
            name = $name
            weight = $weight
        """.trimIndent()
    }
}
