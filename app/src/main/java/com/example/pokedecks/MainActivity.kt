package com.example.pokedecks

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn.setOnClickListener {
            val randomNum = (0..400).random().toString()

            // set to Imageview using Picasso
            Picasso.get()
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$randomNum.png")
                .resize(500, 500)
                .into(pokePic)

            // get pokemon name from poke API
            HitAPITask().execute(
                "https://pokeapi.co/api/v2/pokemon/$randomNum"
            )
        }
    }

    // make inner class here to deal with async operation
    inner class HitAPITask : AsyncTask<String, String, String>() {
        private val TAG = "API"

        override fun doInBackground(vararg params: String?): String {
            val pokeName = StringBuffer()


            try {
                val pokeUrl = URL(params[0])

                // API
                val connection: HttpURLConnection = pokeUrl.openConnection() as HttpURLConnection

                val response = connection.responseCode
                Log.d(TAG, "API: The response code was $response")

                connection.inputStream.buffered().reader().use {
                    pokeName.append(it.readText())
                    Log.d("CHECK", pokeName.toString())

                }

                return pokeName.toString()

            } catch (e: Exception) {
                val errorMessage: String = when (e) {
                    is MalformedURLException -> "downloadXML: Invalid URL ${e.message}"
                    is IOException -> "downloadXML: IO Exception reading data ${e.message}"
                    is SecurityException -> {
                        e.printStackTrace()
                        "Security exception. Need permission ${e.message}"
                    }
                    else -> "Unknown error: ${e.message}"
                }
            }

            return ""
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (result == null) return
            val parentJsonObj = JSONObject(result)

            val pokeId = parentJsonObj.getString("id")
            val pokeName = parentJsonObj.getString("name").uppercase()
            val pokeWeight = parentJsonObj.getString("weight")

            id.text = "ID: $pokeId"
            name.text = "Name: $pokeName"
            weight.text = "Weight: $pokeWeight kg"

        }


    }
}
