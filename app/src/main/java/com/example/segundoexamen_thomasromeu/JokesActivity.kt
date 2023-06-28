package com.example.segundoexamen_thomasromeu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class JokesActivity : AppCompatActivity() {
    private lateinit var categoria: String
    private lateinit var textViewJokesRandom: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jokes)

        textViewJokesRandom = findViewById(R.id.textViewJokeByCategory)

        categoria = intent.getStringExtra("categoria") ?: ""

        getRandomJokeByCategory(categoria)
    }

    private fun getRandomJokeByCategory(categoria: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getRandomJokeByCategory("random?category=$categoria")
            val response = call.body()

            runOnUiThread {
                if (call.isSuccessful) {
                    val joke = response?.jokes ?: "No hay chiste"
                    textViewJokesRandom.text = joke
                } else {
                    Toast.makeText(this@JokesActivity, "Error al obtener un chiste", Toast.LENGTH_SHORT).show()
                    val error = call.errorBody().toString()
                    Log.e("error", error)
                }
            }

        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.chucknorris.io/jokes/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}