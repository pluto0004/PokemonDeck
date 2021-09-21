package com.example.pokedecks

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
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

        if (!isOnline(this)) {
            Snackbar.make(parentLayout, "No Internet", Snackbar.LENGTH_LONG)
                .show()
        }

        val repository = Repository()
        val useCase = UseCase()

        btn.setOnClickListener {
            val randomNum = (0..400).random().toString()

            lifecycleScope.launch {
                // reset here??
                repository.pokeName.setLength(0)

                try {
                    repository.loadPokemon(randomNum)

                    val pokeId = useCase.postExecute(repository.pokeName.toString(), "id")
                    val pokeName = useCase.postExecute(repository.pokeName.toString(), "name")
                    val pokeWeight =
                        useCase.postExecute(repository.pokeName.toString(), "weight")
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

    // still try to understand this
    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
}

