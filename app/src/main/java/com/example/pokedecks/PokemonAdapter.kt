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


private const val TAG = "PokemonAdapter"

class PokemonAdapter(
    private val context: Context,
    private val pokemonList: List<PokemonEntity>
) :
    RecyclerView.Adapter<PokemonViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonViewHolder {

        val view = PokemonRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PokemonViewHolder(view, context, parent)
    }

    override fun onViewRecycled(holderPokemon: PokemonViewHolder) {
        Log.d(TAG, "onViewRecycled called with $holderPokemon")
        holderPokemon.recycleHolder()
        super.onViewRecycled(holderPokemon)
    }

    override fun onBindViewHolder(holderPokemon: PokemonViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder called with $holderPokemon")
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
    private val parent: ViewGroup
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(pokemon: PokemonEntity) {
        binding.tvPokeId.text = context.getString(R.string.poke_id, pokemon.id)
        binding.tvPokeName.text =
            context.getString(R.string.poke_name, pokemon.name.replaceFirstChar { it.uppercase() })
        binding.tvPokeWeight.text = context.getString(R.string.poke_weight, pokemon.weight)
        binding.tvPokeHeight.text = context.getString(R.string.poke_height, pokemon.height)

        for (i in 0 until pokemon.type.size) {
            val chipView: View = LayoutInflater.from(context).inflate(R.layout.chip, parent, false)
            chipView.layoutParams = ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )

            chipView.ch.text = context.getString(
                R.string.poke_type,
                pokemon.type[i]
            )
            setType(chipView.ch, pokemon.type[i])

            binding.chipsHolderLayout.addView(chipView)
        }

        Picasso.get()
            .load(pokemon.image)
            .resize(200, 200)
            .placeholder(R.mipmap.ic_launcher_round)
            .into(binding.ivPokemon)
    }

    fun recycleHolder() {
        binding.chipsHolderLayout.removeAllViews()
    }
}

fun setType(pokemonType: Chip, type: String) {
    try {
        val typeEnum = PokemonType.valueOf(type)
        pokemonType.setChipBackgroundColorResource(typeEnum.color)
    } catch (e: Exception) {
        Log.e(TAG, "No value of $type", e)
    }
}
