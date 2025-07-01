package com.example.rickandmorty

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    private lateinit var ivCharacter: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvSpecies: TextView
    private lateinit var tvStatus: TextView
    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        ivCharacter = findViewById(R.id.imageViewCharacterDetail)
        tvName = findViewById(R.id.textViewDescription)
        tvSpecies = findViewById(R.id.textViewLat)
        tvStatus = findViewById(R.id.textViewLong)
        btnBack = findViewById(R.id.buttonBack)

        btnBack.setOnClickListener {
            finish()
        }


        val character = intent.getParcelableExtra<Character>("character")

        if (character != null) {
            tvName.text = character.name
            tvSpecies.text = "Especie: ${character.species}"
            tvStatus.text = "Estado: ${character.status}"
            Glide.with(this)
                .load(character.image)
                .into(ivCharacter)
        } else {
            tvName.text = "Personaje no encontrado"
        }
    }
}
