package com.example.pokedecks

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var newRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager(this).orientation)


        newRecyclerView = rv_pokemon
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)
        newRecyclerView.addItemDecoration(dividerItemDecoration)

        val parentLayout: View = findViewById(android.R.id.content)
        val networkCheck = NetworkUtils()
        val useCase = UseCase()
        val limit = 10

            if (!networkCheck.isOnline(this)) {
                Snackbar.make(parentLayout, "No Internet", Snackbar.LENGTH_LONG)
                    .show()
            }else{
                lifecycleScope.launch {
                    try {
                        val list = useCase.getPokemon(limit)
                        Log.d(TAG, list.toString())

                        for(i in 0 until list.size){
                            useCase.setDetails(list[i].name)
                        }

                        newRecyclerView.adapter = Adapter(list)

                        loadingBar.visibility = View.GONE
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

