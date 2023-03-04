package com.example.pokedexsample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexsample.PokemonAdapter
import com.example.pokedexsample.data.PokemonClient
import com.example.pokedexsample.data.PokemonListResponse
import com.example.pokedexsample.databinding.FragmentPokemonListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonListFragment : Fragment() {
    private lateinit var binding: FragmentPokemonListBinding
    private lateinit var pokemonAdapter: PokemonAdapter
    private var offset = 0
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onPause() {
        offset = 0
        super.onPause()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         val layoutManager = LinearLayoutManager(requireContext())
        pokemonAdapter = PokemonAdapter(mutableListOf()) { pokemon ->
            val action =
                PokemonListFragmentDirections.navigateToDetail(pokemon.name, pokemon.getImageUrl())
            findNavController().navigate(action)
        }
        binding.pokemonList.adapter = pokemonAdapter
        binding.pokemonList.layoutManager = layoutManager
        binding.pokemonList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (!isLoading && (lastVisibleItem + 5 >= totalItemCount)) {
                    offset += 20
                    getPokemonList(offset)
                }
            }
        })
        getPokemonList(offset)
    }

    private fun getPokemonList(offset: Int) {
        isLoading = true
        val service = PokemonClient.pokemonService
        val call = service.getPokemonList(20, offset)
        call.enqueue(object : Callback<PokemonListResponse> {
            override fun onResponse(
                call: Call<PokemonListResponse>,
                response: Response<PokemonListResponse>
            ) {
                if (response.isSuccessful) {
                    val pokemonListResponse = response.body()
                    if (pokemonListResponse != null) {
                        val pokemonList = pokemonListResponse.results.toMutableList()
                        pokemonAdapter.pokemonList.addAll(pokemonList)
                        pokemonAdapter.notifyDataSetChanged()
                        isLoading = false
                    }
                }
            }

            override fun onFailure(call: Call<PokemonListResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Failed to get Pokemon list", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}
