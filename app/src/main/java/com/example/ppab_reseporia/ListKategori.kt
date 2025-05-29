package com.example.ppab_reseporia

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun CategoryScreen(categoryName: String, navController: NavController) {
    var selectedSortOption by remember { mutableStateOf("Default") }
    var expanded by remember { mutableStateOf(false) }
    val initialFilteredList = allFoodList.filter { it.category == categoryName }
    val sortedFoodList = remember(initialFilteredList, selectedSortOption) {
        when (selectedSortOption) {
            "Rating" -> initialFilteredList.sortedByDescending { it.rating }
            "Waktu Memasak" -> initialFilteredList.sortedBy { it.time }
            "Terpopuler" -> initialFilteredList.sortedByDescending { it.likes }
            "Nama" -> initialFilteredList.sortedBy { it.name }
            else -> initialFilteredList //sesuai urutan data
        }
    }

    Scaffold(
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF0ECCF))
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier
                            .size(28.dp)
                            .clickable { navController.popBackStack() }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = categoryName,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = Color.Black
                    )
                }

                Box(
                    modifier = Modifier
                        .wrapContentSize(Alignment.TopEnd)
                ) {
                    Row(
                        modifier = Modifier
                            .clickable { expanded = true }
                            .background(Color(0xFF6B8A56), MaterialTheme.shapes.small)
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Urutkan",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Sort Options",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(Color.White)
                    ) {
                        DropdownMenuItem(
                            text = { Text("Default", color = Color.Black) },
                            onClick = {
                                selectedSortOption = "Default"
                                expanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Rating", color = Color.Black) },
                            onClick = {
                                selectedSortOption = "Rating"
                                expanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Waktu Memasak", color = Color.Black) },
                            onClick = {
                                selectedSortOption = "Waktu Memasak"
                                expanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Terpopuler", color = Color.Black) },
                            onClick = {
                                selectedSortOption = "Terpopuler"
                                expanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Nama", color = Color.Black) },
                            onClick = {
                                selectedSortOption = "Nama"
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF0ECCF))
        ) {
            if (sortedFoodList.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Tidak ada resep untuk kategori ini.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(4.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(sortedFoodList) { food ->
                        FoodItem(
                            name = food.name,
                            imageRes = food.imageRes,
                            rating = food.rating,
                            likes = food.likes,
                            time = food.time,
                            onClick = { navController.navigate(AlurApp.getDetailRecipeRoute(food.name)) }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCategoryScreen() {
    CategoryScreen(categoryName = "Main Course", navController = rememberNavController())
}