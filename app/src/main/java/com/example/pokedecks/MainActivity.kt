package com.example.pokedecks

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedecks.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding

    private val limitOfPokemonsToLoad = 151

    private val networkCheck = NetworkUtils()
    private val useCase = UseCase()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val context = this

        initRecyclerView()
        
        if (!networkCheck.isOnline(this)) {
            Snackbar.make(binding.layout, "No Internet", Snackbar.LENGTH_LONG)
                .show()
            binding.loadingBar.visibility = View.GONE
        } else {
            lifecycleScope.launch {
                try {
                    binding.loadingBar.visibility = View.VISIBLE
                    val list = useCase.getPokemon(limitOfPokemonsToLoad)

                    //switch back to UI thread
                    newRecyclerView.adapter = PokemonAdapter(context, list)

                    Log.d(TAG, "launch done")

                } catch (e: Exception) {
                    e.printStackTrace()
                    showError()
                    Log.e(TAG, "request failed", e)
                }
                binding.loadingBar.visibility = View.GONE
            }
        }
    }

    private fun initRecyclerView() {
        val dividerItemDecoration =
            DividerItemDecoration(this, LinearLayoutManager(this).orientation)

        newRecyclerView = binding.rvPokemon
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)
        newRecyclerView.addItemDecoration(dividerItemDecoration)
    }

    private fun showError() {
        val parentLayout: View = findViewById(android.R.id.content)
        Snackbar.make(parentLayout, "Request failed", Snackbar.LENGTH_LONG)
            .show()
    }
}

