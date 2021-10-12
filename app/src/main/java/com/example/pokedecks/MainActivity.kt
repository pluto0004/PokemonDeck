package com.example.pokedecks

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedecks.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class MainActivity : BaseActivity(),
    RecyclerItemClickListener.OnRecyclerClickListener {
    private val TAG = "MainActivity"

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding

    private lateinit var list: MutableList<PokemonEntity>
    private val limitOfPokemonsToLoad = 151

    private val networkCheck = NetworkUtils()
    private val useCase = UseCase()
    private val prefConfig = PrefConfig()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate starts with $limitOfPokemonsToLoad")
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activateToolBar(false, binding.toolbar)

        val pokeList: MutableList<PokemonEntity>? = prefConfig.readFile(this)

        initRecyclerView()
        binding.containerMain.rvPokemon.addOnItemTouchListener(
            RecyclerItemClickListener(
                this,
                binding.containerMain.rvPokemon,
                this
            )
        )

        // network check
        if (!networkCheck.isOnline(this)) {
            Snackbar.make(binding.containerMain.loadingBar, "No Internet", Snackbar.LENGTH_LONG)
                .show()
            binding.containerMain.loadingBar.visibility = View.GONE
        } else {
            lifecycleScope.launch {
                try {
                    binding.containerMain.loadingBar.visibility = View.VISIBLE

                    // if there is already a list in preference, use it. Otherwise call API
                    list = (if (pokeList != null && pokeList.size == 151) {
                        pokeList
                    } else useCase.getPokemon(limitOfPokemonsToLoad))

                    // store list to shared preference to avoid API call again
                    prefConfig.writeFile(applicationContext, list)

                    //switch back to UI thread
                    newRecyclerView.adapter = PokemonAdapter(this@MainActivity, list)

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


        Snackbar.make(
            view,
            "Long tap to see the detail of ${list[position].name.replaceFirstChar { it.uppercase() }}",
            Snackbar.LENGTH_LONG
        ).show()
    }

    override fun onItemLongClick(view: View, position: Int) {
        Log.d(TAG, "onItemLongClick: Clicked")
//        val pokemon = PokemonAdapter(this, list).getPokemon(position)
        val pokemon = list.get(position)

        if (pokemon != null) {
            Log.d(TAG, "intent created: Clicked")
            val intent = Intent(this, PokemonDetailsActivity::class.java).apply {
                putExtra(POKEMON_TRANSFER, pokemon)
            }
            startActivity(intent)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Log.d(TAG, "onCreateOptionsMenu called")

        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)

        val menuItem = menu.findItem(R.id.action_search)
        val searchView = menuItem?.actionView as SearchView?
        searchView?.queryHint = "Enter Pokemon Type"

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG, ".onQueryTextSubmit: called with $query")

                if (query != null && query.isNotEmpty()) {
                    val newList =
                        list.filter { it.type.contains(query.replaceFirstChar { it.uppercase() }) } as MutableList<PokemonEntity>

                    newRecyclerView.adapter = PokemonAdapter(this@MainActivity, newList)
                } else {
                    newRecyclerView.adapter = PokemonAdapter(this@MainActivity, list)
                }
                searchView.clearFocus()

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }


        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: Starts")

    }
}

