package com.example.pokedecks

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedecks.databinding.PokemonRecordBinding
import com.google.android.material.chip.Chip
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.chip.view.*


private val TAG = "PokemonAdapter"

class PokemonAdapter(
    private val context: Context,
    private val pokemonList: List<PokemonEntity>
) :
    RecyclerView.Adapter<PokemonViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonViewHolder {
        Log.d("ViewType", viewType.toString())

        var view = PokemonRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PokemonViewHolder(view, context)
    }

    override fun onBindViewHolder(holderPokemon: PokemonViewHolder, position: Int) {

        val currentItem = pokemonList[position]
        Log.d(TAG, currentItem.toString())

        holderPokemon.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }
}

class PokemonViewHolder(
    private val binding: PokemonRecordBinding,
    private val context: Context,
) : RecyclerView.ViewHolder(binding.root) {
    private val container = binding.chipsHolderLayout

    fun bind(pokemon: PokemonEntity) {
        binding.tvPokeId.text = context.getString(R.string.poke_id, pokemon.id)
        binding.tvPokeName.text = context.getString(R.string.poke_name, pokemon.name)
        binding.tvPokeWeight.text = context.getString(R.string.poke_weight, pokemon.weight)
        binding.tvPokeHeight.text = context.getString(R.string.poke_height, pokemon.height)

        //TODO: There is a bug.
        for (i in 0 until pokemon.type.size) {
            val chipView: View = LayoutInflater.from(context).inflate(R.layout.chip, null)
            chipView.layoutParams = ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )
            Log.d("Type", chipView.ch.id.toString())

            chipView.ch.text = context.getString(R.string.poke_type, pokemon.type[i])
            setType(chipView.ch, pokemon.type[i])

            binding.chipsHolderLayout.addView(chipView)
        }

        Picasso.get()
            .load(pokemon.image)
            .resize(200, 200)
            .placeholder(R.mipmap.ic_launcher_round)
            .into(binding.ivPokemon)
    }
}


fun setType(pokemonType: Chip, type: String) {
    pokemonType.setText(type.replaceFirstChar { it.uppercase() })
    val typeEnum = PokemonType.valueOf(type.replaceFirstChar { it.uppercase() })
    pokemonType.setChipBackgroundColorResource(typeEnum.color)
}
