package com.example.pokedecks

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import androidx.preference.PreferenceManager
import com.example.pokedecks.databinding.ActivitySearchBinding

private const val TAG = "SearchActivity"

class SearchActivity : BaseActivity() {
    private var searchView: SearchView? = null

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activateToolBar(true, binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Log.d(TAG, ".onCreateOptionsMenu: Starts")
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext)

        // Clear the query
        sharedPref.edit().putString(POKEMON_QUERY, "").apply()

        menuInflater.inflate(R.menu.menu_search, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView
        val searchableInfo = searchManager.getSearchableInfo(componentName)
        searchView?.setSearchableInfo(searchableInfo)
        searchView?.isIconified = false

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG, ".onQueryTextSubmit: called with $query")

                sharedPref.edit().putString(POKEMON_QUERY, query).apply()
                searchView?.clearFocus()

                finish()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        searchView?.setOnCloseListener {
            finish()
            false
        }

        Log.d(TAG, "onCreateOptionsMenu: returning")
        return true
    }


}
