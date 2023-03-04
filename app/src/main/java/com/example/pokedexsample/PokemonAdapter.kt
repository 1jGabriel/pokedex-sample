package com.example.pokedexsample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedexsample.data.Pokemon
import com.example.pokedexsample.databinding.PokemonItemBinding

class PokemonAdapter(
    var pokemonList: MutableList<Pokemon>,
    val clickListener: (pokemon: Pokemon) -> Unit
) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PokemonItemBinding.inflate(inflater, parent, false)
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        holder.bind(pokemon)
        holder.itemView.setOnClickListener {
            clickListener(pokemon)
        }
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    class PokemonViewHolder(private val binding: PokemonItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: Pokemon) {
            binding.pokemonName.text = pokemon.name
            Glide.with(binding.pokemonImage.context)
                .load(pokemon.getImageUrl())
                .into(binding.pokemonImage)
        }
    }
}