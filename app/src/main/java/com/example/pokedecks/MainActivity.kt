package com.example.pokedecks

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = Repository()
        val useCase = UseCase()

        btn.setOnClickListener {
            val randomNum = (0..400).random().toString()

            lifecycleScope.launch {
                // reset here??
                repository.pokeName.setLength(0)
                repository.loadPokemon(randomNum)

                val pokeId = useCase.postExecute(repository.pokeName.toString(), "id")
                val pokeName = useCase.postExecute(repository.pokeName.toString(), "name")
                val pokeWeight = useCase.postExecute(repository.pokeName.toString(), "weight")
                Picasso.get()
                    .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$randomNum.png")
                    .resize(500, 500)
                    .into(pokePic)

                id.text = pokeId
                name.text = pokeName
                weight.text = pokeWeight
                Log.d(TAG, "launch done")
            }

        }
    }
}
