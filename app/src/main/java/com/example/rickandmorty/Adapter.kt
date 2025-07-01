package com.example.rickandmorty

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView
import com.bumptech.glide.Glide


class Adapter(private val characters: List<Character>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    lateinit var onItemClickListener: (Character) -> Unit

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val tvName: TextView = view.findViewById(R.id.textViewMag)
        private val tvSpecies: TextView = view.findViewById(R.id.textViewPlace)
        private val ivImage: ImageView = view.findViewById(R.id.imageViewCharacter)

        fun bind(character: Character) {
            tvName.text = character.name
            tvSpecies.text = character.species

            Glide.with(view.context)
                .load(character.image)
                .into(ivImage)

            view.setOnClickListener {
                onItemClickListener(character)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = characters[position]
        holder.bind(character)
    }
}
