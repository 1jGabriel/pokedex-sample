package com.example.pokedexsample.data

data class Pokemon(
    val name: String,
    val url: String,
) {
    fun getImageUrl() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${
        url.split("/").filter { it.isNotBlank() }.last()
    }.png"
}

