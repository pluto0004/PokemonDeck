package com.example.pokedecks

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.pokedecks.databinding.ActivityMainBinding

internal const val POKEMON_TRANSFER = "POKEMON_TRANSFER"

open class BaseActivity : AppCompatActivity() {
    private val TAG = "Base Activity"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    internal fun activateToolBar(enableHome: Boolean) {
        Log.d(TAG, "activate toolbar")

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(enableHome)
    }
}
