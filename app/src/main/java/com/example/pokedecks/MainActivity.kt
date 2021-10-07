package com.example.pokedecks

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedecks.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : BaseActivity(), RecyclerItemClickListener.OnRecyclerClickListener {
    private val TAG = "MainActivity"
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding
    private lateinit var list: MutableList<PokemonEntity>

    private val limitOfPokemonsToLoad = 151

    private val networkCheck = NetworkUtils()
    private val useCase = UseCase()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val context = this

        initRecyclerView()
        binding.containerMain.rvPokemon.addOnItemTouchListener(
            RecyclerItemClickListener(
                this,
                binding.containerMain.rvPokemon,
                this
            )
        )

        if (!networkCheck.isOnline(this)) {
            Snackbar.make(binding.containerMain.loadingBar, "No Internet", Snackbar.LENGTH_LONG)
                .show()
            binding.containerMain.loadingBar.visibility = View.GONE
        } else {
            lifecycleScope.launch {
                try {
                    binding.containerMain.loadingBar.visibility = View.VISIBLE
                    list = useCase.getPokemon(limitOfPokemonsToLoad)

                    //switch back to UI thread
                    newRecyclerView.adapter = PokemonAdapter(context, list)

                    Log.d(TAG, "launch done")

                } catch (e: Exception) {
                    e.printStackTrace()
                    showError()
                    Log.e(TAG, "request failed", e)
                }
                binding.containerMain.loadingBar.visibility = View.GONE
            }
        }
    }

    private fun initRecyclerView() {
        val dividerItemDecoration =
            DividerItemDecoration(this, LinearLayoutManager(this).orientation)

        newRecyclerView = binding.containerMain.rvPokemon
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)
        newRecyclerView.addItemDecoration(dividerItemDecoration)
    }

    private fun showError() {
        val parentLayout: View = findViewById(android.R.id.content)
        Snackbar.make(parentLayout, "Request failed", Snackbar.LENGTH_LONG)
            .show()
    }

    override fun onItemClick(view: View, position: Int) {
        Log.d(TAG, "onItemClick: Clicked")
        Snackbar.make(view, "Normal tap at position: $position", Snackbar.LENGTH_SHORT).show()
    }

    override fun onItemLongClick(view: View, position: Int) {
        Log.d(TAG, "onItemLongClick: Clicked")
        val pokemon = PokemonAdapter(this, list).getPokemon(position)

        if (pokemon != null) {
            Log.d(TAG, "intent created: Clicked")
            val intent = Intent(this, PokemonDetailsActivity::class.java).apply {
                putExtra(POKEMON_TRANSFER, pokemon)
            }
            startActivity(intent)
        }
        Snackbar.make(view, "Long tap at position: $position", Snackbar.LENGTH_SHORT).show()
    }
}

