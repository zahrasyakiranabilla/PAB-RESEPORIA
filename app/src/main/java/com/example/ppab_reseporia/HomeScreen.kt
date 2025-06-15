package com.example.ppab_reseporia

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

// Simple wrapper yang manggil HomeScreen dari Komponen.kt
@Composable
fun MainHomeScreen(navController: NavController, foodList: List<DataResep>) {
    // Pakai HomeScreen yang udah ada di Komponen.kt
    HomeScreen(navController = navController, foodList = foodList)
}