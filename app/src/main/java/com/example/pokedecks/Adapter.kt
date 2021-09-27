package com.example.pokedecks

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.squareup.picasso.Picasso


private val TAG = "Adapter"

class Adapter(private val pokemonList: ArrayList<PokemonEntity>):
    RecyclerView.Adapter<Adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_record, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = pokemonList[position]

        holder.pokeId.setText("ID: ${currentItem.id}")
        holder.pokeName.setText(currentItem.name.replaceFirstChar { it.uppercase() })
        holder.pokeWeight.setText("${currentItem.weight} kg")
        holder.pokeHeight.setText("${currentItem.height} cm")

        // ToDo: Find the way to improve here(loop?)
        if (currentItem.type.size > 1){
            setType(holder.pokemonType1, currentItem.type[0])
            setType(holder.pokemonType2, currentItem.type[1])

        }else{
            setType(holder.pokemonType1, currentItem.type[0])
            holder.pokemonType2.visibility = View.INVISIBLE
        }

        Picasso.get()
            .load(currentItem.image)
            .resize(200,200)
            .placeholder(R.mipmap.ic_launcher_round)
            .into(holder.pokemonImage)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    fun setType(pokemonType:Chip, type:String){
        pokemonType.setText(type.replaceFirstChar { it.uppercase() })
        Log.d("Type", R.color.grass.toString())

        // ToDo: Find the way to improve here
        when(type){
            "grass" -> pokemonType.setChipBackgroundColorResource(R.color.grass)
            "poison" -> pokemonType.setChipBackgroundColorResource(R.color.poison)
            "fire" -> pokemonType.setChipBackgroundColorResource(R.color.fire)
            "water" -> pokemonType.setChipBackgroundColorResource(R.color.water)
            "bug" -> pokemonType.setChipBackgroundColorResource(R.color.bug)
            "ghost" -> pokemonType.setChipBackgroundColorResource(R.color.ghost)
            "flying" -> pokemonType.setChipBackgroundColorResource(R.color.flying)
            "dragon" -> pokemonType.setChipBackgroundColorResource(R.color.dragon)
            "normal" -> pokemonType.setChipBackgroundColorResource(R.color.normal)
            "electric" -> pokemonType.setChipBackgroundColorResource(R.color.electric)
            "ground" -> pokemonType.setChipBackgroundColorResource(R.color.ground)
            "fairy" -> pokemonType.setChipBackgroundColorResource(R.color.fairy)
            "rock" -> pokemonType.setChipBackgroundColorResource(R.color.rock)
            "psychic" -> pokemonType.setChipBackgroundColorResource(R.color.psychic)
            "fighting" -> pokemonType.setChipBackgroundColorResource(R.color.fighting)
            "ice" -> pokemonType.setChipBackgroundColorResource(R.color.ice)

            else -> pokemonType.setChipBackgroundColorResource(R.color.white)
        }
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val pokeId: TextView = itemView.findViewById(R.id.tv_pokeId)
        val pokeName: TextView = itemView.findViewById(R.id.tv_pokeName)
        val pokeWeight: TextView = itemView.findViewById(R.id.tv_pokeWeight)
        val pokeHeight: TextView = itemView.findViewById(R.id.tv_pokeHeight)
        val pokemonImage: ImageView = itemView.findViewById(R.id.iv_pokemon)
        val pokemonType1: Chip = itemView.findViewById(R.id.ch_type1)
        val pokemonType2: Chip = itemView.findViewById(R.id.ch_type2)
    }

}
