package com.example.segundoexamen_thomasromeu

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var textViewJokesRandom: TextView
    private lateinit var buttonRandomJoke: Button
    private lateinit var buttonCategories: Button
    private lateinit var videoView: VideoView
    private lateinit var buttonMute : ImageButton
    private var isMuted: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewJokesRandom = findViewById(R.id.textViewJokesRandom)
        buttonRandomJoke = findViewById(R.id.buttonRandomJoke)
        buttonCategories = findViewById(R.id.buttonGoToCategories)
        videoView = findViewById(R.id.videoView3)
        buttonMute = findViewById(R.id.buttonMute)

        videoView.setVideoPath("android.resource://"+packageName+"/"+R.raw.chucknorris)

        videoView.start()

        buttonMute.setOnClickListener {
            if (!isMuted) {
                muteVideo()
                isMuted = true
            } else {
                unmuteVideo()
                isMuted = false
            }
        }

        buttonRandomJoke.setOnClickListener {
            getRandomJoke()
        }

        buttonCategories.setOnClickListener {
            goToCategories()
        }

    }

    private fun muteVideo() {
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val originalVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0)
    }

    private fun unmuteVideo() {
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0)
    }
    private fun goToCategories() {
        val intent = Intent(this, CategoriesActivity::class.java)
        startActivity(intent)
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