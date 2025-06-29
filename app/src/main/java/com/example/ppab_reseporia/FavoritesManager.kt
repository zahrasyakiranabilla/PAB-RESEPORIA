package com.example.ppab_reseporia

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

object FavoritesManager {
    private val _favoriteRecipes = mutableStateListOf<DataResep>()
    val favoriteRecipes: SnapshotStateList<DataResep> = _favoriteRecipes

    // Load favorites from SharedPreferences
    fun loadFavorites(context: Context) {
        val sharedPref = context.getSharedPreferences("favorites", Context.MODE_PRIVATE)
        val favoriteNames = sharedPref.getStringSet("favorite_names", emptySet()) ?: emptySet()

        _favoriteRecipes.clear()
        favoriteNames.forEach { name ->
            allFoodList.find { it.name == name }?.let { recipe ->
                _favoriteRecipes.add(recipe)
            }
        }
    }

    // Save favorites to SharedPreferences
    private fun saveFavorites(context: Context) {
        val sharedPref = context.getSharedPreferences("favorites", Context.MODE_PRIVATE)
        val favoriteNames = _favoriteRecipes.map { it.name }.toSet()

        with(sharedPref.edit()) {
            putStringSet("favorite_names", favoriteNames)
            apply()
        }
    }

    // Add recipe to favorites
    fun addToFavorites(context: Context, recipe: DataResep) {
        if (!_favoriteRecipes.contains(recipe)) {
            _favoriteRecipes.add(recipe)
            saveFavorites(context)
        }
    }

    // Remove recipe from favorites
    fun removeFromFavorites(context: Context, recipe: DataResep) {
        _favoriteRecipes.remove(recipe)
        saveFavorites(context)
    }

    // Check if recipe is in favorites
    fun isFavorite(recipe: DataResep): Boolean {
        return _favoriteRecipes.contains(recipe)
    }

    // Toggle favorite status
    fun toggleFavorite(context: Context, recipe: DataResep) {
        if (isFavorite(recipe)) {
            removeFromFavorites(context, recipe)
        } else {
            addToFavorites(context, recipe)
        }
    }
}