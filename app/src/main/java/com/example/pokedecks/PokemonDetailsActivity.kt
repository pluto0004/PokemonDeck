package com.example.pokedecks

import android.os.Bundle
import com.example.pokedecks.databinding.ActivityPokemonDetailsBinding
import com.squareup.picasso.Picasso

class PokemonDetailsActivity : BaseActivity() {
    private lateinit var binding: ActivityPokemonDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activateToolBar(true)

        val pokemon = intent.getParcelableExtra<PokemonEntity>(POKEMON_TRANSFER) as PokemonEntity

        binding.containerPokemon.pokemonNameDetail.text = pokemon.name
        binding.containerPokemon.pokemonHeightDetail.text = pokemon.height
        binding.containerPokemon.pokemonWeightDetail.text = pokemon.weight
        Picasso.get()
            .load(pokemon.image)
            .error(R.drawable.placeholder)
            .resize(400, 400)
            .placeholder(R.drawable.placeholder)
            .into(binding.containerPokemon.pokemonPhotoDetail)
    }
}

