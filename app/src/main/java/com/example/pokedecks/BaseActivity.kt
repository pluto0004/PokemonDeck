package com.example.pokedecks

import android.util.Log
import androidx.appcompat.app.AppCompatActivity

internal const val POKEMON_TRANSFER = "POKEMON_TRANSFER"
internal const val POKEMON_QUERY = "POKEMON_QUERY"

open class BaseActivity : AppCompatActivity() {
    private val TAG = "BaseActivity"

    internal fun activateToolBar(enableHome: Boolean, toolbar: androidx.appcompat.widget.Toolbar) {
        Log.d(TAG, "activate toolbar")

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(enableHome)
    }
}
