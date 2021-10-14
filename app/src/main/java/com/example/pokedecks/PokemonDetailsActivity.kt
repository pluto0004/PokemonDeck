package com.example.pokedecks

import android.os.Bundle
import android.util.Log
import com.example.pokedecks.databinding.ActivityPokemonDetailsBinding
import com.squareup.picasso.Picasso

private const val TAG = "PokemonDetailsActivity"

class PokemonDetailsActivity : BaseActivity() {
    private lateinit var binding: ActivityPokemonDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: Starts")

        super.onCreate(savedInstanceState)
        binding = ActivityPokemonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activateToolBar(true, binding.toolbar)

        val pokemon = intent.getParcelableExtra<PokemonEntity>(POKEMON_TRANSFER) as PokemonEntity

        binding.containerPokemon.pokemonNameDetail.text =
            pokemon.name.replaceFirstChar { it.uppercase() }
        binding.containerPokemon.pokemonHeightDetail.text =
            this.getString(R.string.poke_height_detail, pokemon.height)
        binding.containerPokemon.pokemonWeightDetail.text =
            this.getString(R.string.poke_weight_detail, pokemon.weight)
        Picasso.get()
            .load(pokemon.image)
            .error(R.drawable.placeholder)
            .resize(400, 400)
            .placeholder(R.drawable.placeholder)
            .into(binding.containerPokemon.pokemonPhotoDetail)
    }

}

