package com.example.pokedecks

import androidx.appcompat.app.AppCompatActivity

internal const val POKEMON_TRANSFER = "POKEMON_TRANSFER"
internal const val POKEMON_QUERY = "POKEMON_QUERY"
private const val TAG = "BaseActivity"

open class BaseActivity : AppCompatActivity() {

    internal fun activateToolBar(enableHome: Boolean, toolbar: androidx.appcompat.widget.Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(enableHome)
    }
}
