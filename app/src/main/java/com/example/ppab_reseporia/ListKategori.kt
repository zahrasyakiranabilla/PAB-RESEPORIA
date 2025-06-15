package com.example.ppab_reseporia

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun CategoryTopBar(
    navController: NavController,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onBack: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF5F8150))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Back button + Logo
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .clickable { onBack() }
                    .size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(R.drawable.logoreseporia),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        // Navigate balik ke homepage dan clear back stack
                        navController.navigate(AlurApp.HOME_SCREEN) {
                            popUpTo(AlurApp.HOME_SCREEN) {
                                inclusive = true
                            }
                        }
                    }
            )
        }

        // Search field
        TextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            placeholder = {
                Text(
                    "Search...",
                    color = Color.Gray
                )
            },
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            shape = RoundedCornerShape(24.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.Gray
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(
                        onClick = { onSearchQueryChange("") }
                    ) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = "Clear",
                            tint = Color.Gray
                        )
                    }
                }
            },
            singleLine = true
        )

        // Right icons
        Icon(
            Icons.Default.Favorite,
            contentDescription = "Favorite",
            tint = Color.White,
            modifier = Modifier.clickable {
                navController.navigate(AlurApp.FAVORITE_RECIPES_SCREEN)
            }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            Icons.Default.Person,
            contentDescription = "Profile",
            tint = Color.White,
            modifier = Modifier.clickable {
                navController.navigate(AlurApp.PROFILE_SCREEN)
            }
        )
    }
}

@Composable
fun CategoryScreen(categoryName: String, navController: NavController) {
    var selectedSortOption by remember { mutableStateOf("Default") }
    var expanded by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    val initialFilteredList = allFoodList.filter { it.category == categoryName }

    // Filter berdasarkan search query dan sort
    val filteredAndSortedList = remember(initialFilteredList, selectedSortOption, searchQuery) {
        val searchFiltered = if (searchQuery.isBlank()) {
            initialFilteredList
        } else {
            initialFilteredList.filter {
                it.name.contains(searchQuery, ignoreCase = true)
            }
        }

        when (selectedSortOption) {
            "Rating" -> searchFiltered.sortedByDescending { it.rating }
            "Waktu Memasak" -> searchFiltered.sortedBy { it.time }
            "Terpopuler" -> searchFiltered.sortedByDescending { it.likes }
            "Nama" -> searchFiltered.sortedBy { it.name }
            else -> searchFiltered //sesuai urutan data
        }
    }

    Scaffold(
        topBar = {
            CategoryTopBar(
                navController = navController,
                searchQuery = searchQuery,
                onSearchQueryChange = { searchQuery = it },
                onBack = { navController.popBackStack() }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF0ECCF))
        ) {
            // Category name + Sort dropdown
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = categoryName,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = Color.Black,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )

                Box(
                    modifier = Modifier.wrapContentSize(Alignment.TopEnd)
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

            // Food Grid
            if (filteredAndSortedList.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (searchQuery.isBlank()) {
                            "Tidak ada resep untuk kategori ini."
                        } else {
                            "Tidak ada resep yang ditemukan untuk \"$searchQuery\""
                        },
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
                    items(filteredAndSortedList) { food ->
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