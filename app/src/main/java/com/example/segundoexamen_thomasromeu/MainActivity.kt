package com.example.segundoexamen_thomasromeu

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var textViewJokesRandom: TextView
    private lateinit var buttonRandomJoke: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewJokesRandom = findViewById(R.id.textViewJokesRandom)
        buttonRandomJoke = findViewById(R.id.buttonRandomJoke)

        buttonRandomJoke.setOnClickListener {
            getRandomJoke()
        }

    }

    private fun getRandomJoke() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getRandomJoke()
            val response = call.body()

            runOnUiThread {
                if (call.isSuccessful) {
                    val joke = response?.jokes ?: "No hay chiste"
                    textViewJokesRandom.text = joke
                } else {
                    Toast.makeText(this@MainActivity, "Error al obtener un chiste", Toast.LENGTH_SHORT).show()
                    val error = call.errorBody().toString()
                    Log.e("error", error)
                }
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(URL_CHUCKNORRIS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object {
        const val URL_CHUCKNORRIS = "https://api.chucknorris.io/jokes/"
    }
}