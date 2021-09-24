package com.example.pokedecks

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val parentLayout: View = findViewById(android.R.id.content)
        val networkCheck = NetworkUtils()
        val useCase = UseCase()

        btn.setOnClickListener {
            val randomNum = (0..400).random().toString()
            if (!networkCheck.isOnline(this)) {
                Snackbar.make(parentLayout, "No Internet", Snackbar.LENGTH_LONG)
                    .show()
            }

            lifecycleScope.launch {
                try {
                    val pokeId = useCase.generatePokemonDetails(randomNum, "id")
                    val pokeName = useCase.generatePokemonDetails(randomNum, "name")
                    val pokeWeight =
                        useCase.generatePokemonDetails(randomNum, "weight")
                    Picasso.get()
                        .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$randomNum.png")
                        .placeholder(R.mipmap.ic_launcher_round)
                        .resize(200, 200)
                        .into(pokePic)

                    id.text = "ID: $pokeId"
                    name.text = pokeName
                    weight.text = "$pokeWeight kg"

                    Log.d(TAG, "launch done")

                } catch (e: Exception) {
                    e.printStackTrace()
                    showError()
                    Log.d(TAG, "request failed")
                }
            }
        }
    }

    private fun showError() {
        val parentLayout: View = findViewById(android.R.id.content)
        Snackbar.make(parentLayout, "Request failed", Snackbar.LENGTH_LONG)
            .show()
    }
}

