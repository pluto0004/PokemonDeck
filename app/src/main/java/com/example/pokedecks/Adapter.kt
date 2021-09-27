package com.example.pokedecks

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.pokemon_record.*
import java.net.URL

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

        Log.d(TAG, currentItem.weight)
        Log.d(TAG, currentItem.image)

        Picasso.get()
            .load(currentItem.image)
            .resize(200,200)
            .placeholder(R.mipmap.ic_launcher_round)
            .into(holder.pokemonImage)

    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val pokeId: TextView = itemView.findViewById(R.id.tv_pokeId)
        val pokeName: TextView = itemView.findViewById(R.id.tv_pokeName)
        val pokeWeight: TextView = itemView.findViewById(R.id.tv_pokeWeight)
        val pokeHeight: TextView = itemView.findViewById(R.id.tv_pokeHeight)
        val pokemonImage: ImageView = itemView.findViewById(R.id.iv_pokemon)
    }

}
