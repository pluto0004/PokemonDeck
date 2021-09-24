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
            val randomNum = (1..400).random().toString()
            if (!networkCheck.isOnline(this)) {
                Snackbar.make(parentLayout, "No Internet", Snackbar.LENGTH_LONG)
                    .show()
            }

            lifecycleScope.launch {
                try {
                    useCase.getPokemon(randomNum)

                    val pokeId = useCase.generateDetails("id")
                    val pokeName = useCase.generateDetails("name")
                    val pokeWeight =
                        useCase.generateDetails("weight")
                    Picasso.get()
                        .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$randomNum.png")
                        .placeholder(R.mipmap.ic_launcher_round)
                        .resize(200, 200)
                        .into(pokePic)

                    id.text = "ID: $pokeId"
                    name.text = pokeName.toString()
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

