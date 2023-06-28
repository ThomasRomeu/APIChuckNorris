package com.example.segundoexamen_thomasromeu

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CategoriesActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CategoriaAdapter
    private var listOfCategories = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        recyclerView = findViewById(R.id.recycler)

        recyclerView.layoutManager = LinearLayoutManager(this)
        getListOfCategories()

        adapter = CategoriaAdapter(listOfCategories)
        recyclerView.adapter = adapter
        adapter.submitList(listOfCategories)
        adapter.categoriaSeleccionada = { categoria ->
            val intent = Intent(this, JokesActivity::class.java)
            intent.putExtra("categoria", categoria)
            startActivity(intent)
        }

    }

    private fun getListOfCategories() {
        CoroutineScope(Dispatchers.IO).launch {
            val call =
                getRetrofit().create(ApiService::class.java).getCategories("categories")
            val response = call.body()

            runOnUiThread {
                if (call.isSuccessful) {
                    if (call != null) {
                        listOfCategories.addAll(response!!)
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    Toast.makeText(this@CategoriesActivity, "Error", Toast.LENGTH_SHORT).show()
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