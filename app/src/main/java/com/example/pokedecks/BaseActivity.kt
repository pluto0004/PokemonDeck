package com.example.pokedecks

import android.util.Log
import androidx.appcompat.app.AppCompatActivity

internal const val POKEMON_TRANSFER = "POKEMON_TRANSFER"

open class BaseActivity : AppCompatActivity() {
    private val TAG = "BaseActivity"
//    private lateinit var binding: ActivityMainBinding

    internal fun activateToolBar(enableHome: Boolean) {
        Log.d(TAG, "activate toolbar")

        //TODO: Find way to use viewBinding here
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(enableHome)

//        setSupportActionBar(binding.toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(enableHome)
    }
}
