package com.example.rickandmorty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Adapter
    private lateinit var btnAll: Button
    private lateinit var btnAlive: Button
    private lateinit var btnHumans: Button
    private lateinit var btnAliens: Button

    private var characterList = mutableListOf<Character>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvTitle = findViewById(R.id.textViewTitle)
        recyclerView = findViewById(R.id.recyclerQuakes)

        btnAll = findViewById(R.id.btnAll)
        btnAlive = findViewById(R.id.btnAlive)
        btnHumans = findViewById(R.id.btnHumans)
        btnAliens = findViewById(R.id.btnAliens)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = Adapter(characterList)
        recyclerView.adapter = adapter

        adapter.onItemClickListener = { character ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("character", character)
            startActivity(intent)
        }

        // Botones con filtros que hacen las llamadas nuevas a la API
        btnAll.setOnClickListener {
            fetchCharacters()
        }

        btnAlive.setOnClickListener {
            fetchCharacters(status = "alive")
        }

        btnHumans.setOnClickListener {
            fetchCharacters(species = "human")
        }

        btnAliens.setOnClickListener {
            fetchCharacters(species = "alien")
        }

        fetchCharacters()
    }

    private fun fetchCharacters(status: String? = null, species: String? = null) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getCharacters(status, species)
            val response = call.body()

            runOnUiThread {
                if (call.isSuccessful && response != null) {
                    characterList.clear()
                    characterList.addAll(response.results)
                    adapter.notifyDataSetChanged()

                    val filtro = when {
                        status != null -> " (Status: ${status.capitalize()})"
                        species != null -> " (Especie: ${species.capitalize()})"
                        else -> ""
                    }
                    tvTitle.text = "Personajes$filtro"
                } else {
                    tvTitle.text = "Error al cargar personajes"
                }
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object {
        const val BASE_URL = "https://rickandmortyapi.com/api/"
    }
}
