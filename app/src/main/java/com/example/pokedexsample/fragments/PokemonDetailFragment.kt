package com.example.pokedexsample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.pokedexsample.R
import com.example.pokedexsample.databinding.FragmentPokemonDetailBinding

class PokemonDetailFragment : Fragment() {

    private lateinit var binding: FragmentPokemonDetailBinding
    private val args: PokemonDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pokemonName = args.name
        val pokemonUrl = args.imageUrl
        findNavController().currentDestination?.label = args.name
        binding.pokemonName.text = pokemonName
        Glide.with(requireContext())
            .load(pokemonUrl)
            .centerCrop()
            .into(binding.pokemonImage)
    }
}
