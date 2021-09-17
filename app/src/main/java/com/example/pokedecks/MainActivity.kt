package com.example.pokedecks

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
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
            // push the button then get the data from API
            var randomNum = (0..400).random().toString()
            HitAPITask().execute("https://pokeapi.co/api/v2/pokemon/$randomNum")
        }
    }

    // make inner class here to deal with async operation
    inner class HitAPITask : AsyncTask<String, String, String>() {
        private val TAG = "API"

        override fun doInBackground(vararg params: String?): String {
            val apiResult = StringBuffer()


            try {
                val url = URL(params[0])
                // API
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                val response = connection.responseCode
                Log.d(TAG, "API: The response code was $response")

                connection.inputStream.buffered().reader().use {
                    apiResult.append(it.readText())
                    Log.d("CHECK", apiResult.toString())

                }

                // from here parsing json
                val jsonText = apiResult.toString()

                return jsonText

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

            textView.text = parentJsonObj.getString("name")
        }
    }
}
