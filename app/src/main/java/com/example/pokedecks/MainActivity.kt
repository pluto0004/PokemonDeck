package com.example.pokedecks

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedecks.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"

class MainActivity : BaseActivity(),
    RecyclerItemClickListener.OnRecyclerClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var pokemonAdapter: PokemonAdapter
    private val limitOfPokemonsToLoad = 151

    val pokemonViewModel: PokemonViewModel by viewModels()
    val savingList = SavingList()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate starts with $limitOfPokemonsToLoad")
        super.onCreate(savedInstanceState)
        val networkCheck = NetworkUtils()

        pokemonAdapter = PokemonAdapter(this, EMPTY_POKEMON_LIST)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activateToolBar(false, binding.toolbar)

        // observe change in the pokemonList in viewModel
        pokemonViewModel.pokemonList.observe(this,
            { pokemonList ->
                pokemonAdapter.updateList(
                    (pokemonList ?: EMPTY_POKEMON_LIST)
                )
                if (savingList.readFile(applicationContext, "masterPokemonList").isEmpty()) {
                    savingList.writeFile(applicationContext, pokemonList, "masterPokemonList")
                }
                savingList.writeFile(applicationContext, pokemonList, "currentPokemonList")
            })


        initRecyclerView()
        binding.containerMain.rvPokemon.addOnItemTouchListener(
            RecyclerItemClickListener(
                this,
                binding.containerMain.rvPokemon,
                this
            )
        )

        // network and master list check
        val isOnline = networkCheck.isOnline(this)
        val hasMasterExisted = savingList.readFile(
            applicationContext,
            "masterPokemonList"
        ).isNotEmpty()

        if (!isOnline && !hasMasterExisted
        ) {
            Snackbar.make(
                binding.containerMain.loadingBar,
                "Failed to load the list. Please connect to Internet",
                Snackbar.LENGTH_LONG
            )
                .show()
            binding.containerMain.loadingBar.visibility = View.GONE

        } else {
            lifecycleScope.launch {
                try {
                    binding.containerMain.loadingBar.visibility = View.VISIBLE

                    if (!hasMasterExisted) {
                        pokemonViewModel.downloadPokemonList(limitOfPokemonsToLoad)

                    } else {
                        pokemonAdapter.updateList(
                            savingList.readFile(
                                applicationContext,
                                "masterPokemonList"
                            )
                        )
                    }

                    //switch back to UI thread
                    newRecyclerView.adapter = pokemonAdapter


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
        Snackbar.make(binding.root, "Request failed", Snackbar.LENGTH_LONG)
            .show()
    }

    override fun onItemClick(view: View, position: Int) {
        Snackbar.make(
            view,
            "Tap longer to see the details",
            Snackbar.LENGTH_LONG
        ).show()
    }

    override fun onItemLongClick(view: View, position: Int) {
        Log.d(TAG, "onItemLongClick: Clicked")
        val pokemon = pokemonAdapter.getPokemon(position)

        Log.d(TAG, "intent created: Clicked")
        val intent = Intent(this, PokemonDetailsActivity::class.java).apply {
            putExtra(POKEMON_TRANSFER, pokemon)
        }
        startActivity(intent)
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
                Log.d(TAG, "onQueryTextSubmit called with $query")

                if (query != null && query.isNotEmpty()) {
                    pokemonViewModel.searchQuery(
                        savingList.readFile(
                            applicationContext,
                            "masterPokemonList"
                        ), query
                    )
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


}

