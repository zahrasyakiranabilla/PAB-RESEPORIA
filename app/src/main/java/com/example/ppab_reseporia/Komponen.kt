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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.text.style.TextAlign

@Composable
fun TopBar(
    navController: NavController,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF5F8150))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
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

        TextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            placeholder = {
                Text(
                    "Cari resep...",
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
fun PromoBanner() {
    val bannerImages = listOf(
        R.drawable.banner1,
        R.drawable.banner2,
        R.drawable.banner3
    )

    val pagerState = rememberPagerState(pageCount = { bannerImages.size })

    // Auto-slide effect
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000) // Ganti banner setiap 3 detik
            val nextPage = (pagerState.currentPage + 1) % bannerImages.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .padding(top = 16.dp, start = 8.dp, end = 8.dp, bottom = 8.dp)
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) { page ->
                Image(
                    painter = painterResource(bannerImages[page]),
                    contentDescription = "Banner ${page + 1}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }

        // Indicator dots
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 12.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(bannerImages.size) { index ->
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(
                            color = if (index == pagerState.currentPage)
                                Color(0xFF5F8150)
                            else
                                Color.LightGray.copy(alpha = 0.6f),
                            shape = CircleShape
                        )
                )
                if (index < bannerImages.size - 1) {
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }
}

@Composable
fun CategoryTabs(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    val categories = listOf("Appetizer", "Main Course", "Dessert", "Drinks")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(30.dp)
            )
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            categories.forEach { category ->
                Box(
                    modifier = Modifier
                        .background(
                            color = if (selectedCategory == category)
                                Color(0xFF5F8150)
                            else
                                Color.Transparent,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .clickable { onCategorySelected(category) }
                        .padding(horizontal = 12.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = category,
                        color = if (selectedCategory == category) Color.White else Color.Black,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold  // Semua text bold
                    )
                }
            }
        }
    }
}

@Composable
fun FoodGrid(
    foodList: List<DataResep>,
    navController: NavController,
    searchQuery: String = ""
) {
    // Filter resep berdasarkan search query
    val filteredFoodList = if (searchQuery.isBlank()) {
        foodList
    } else {
        foodList.filter {
            it.name.contains(searchQuery, ignoreCase = true)
        }
    }

    // Tampilkan pesan jika tidak ada hasil
    if (filteredFoodList.isEmpty() && searchQuery.isNotBlank()) {
        Column(
            modifier = Modifier
                .fillMaxSize()  // ← Ubah dari fillMaxWidth ke fillMaxSize
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center  // ← Tambahin ini
        ) {
            Icon(
                Icons.Default.Search,
                contentDescription = "No Results",
                tint = Color.Gray,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Tidak ada resep yang ditemukan",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
            Text(
                text = "Coba kata kunci yang lain",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
        return
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxSize()  // ← Ubah dari fillMaxHeight ke fillMaxSize
            .padding(8.dp),
        contentPadding = PaddingValues(4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        itemsIndexed(filteredFoodList) { _, food ->
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

@Composable
fun FoodItem(name: String, imageRes: Int, rating: Double, likes: Int, time: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(4.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "Food",
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 2.dp),
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(2.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.Star,
                    contentDescription = "Rating",
                    tint = Color(0xFFFFD700),
                    modifier = Modifier.size(10.dp)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(text = rating.toString(), style = MaterialTheme.typography.labelSmall)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Likes",
                    tint = Color.Red,
                    modifier = Modifier.size(10.dp)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(text = likes.toString(), style = MaterialTheme.typography.labelSmall)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.AccessTime,
                    contentDescription = "Time",
                    modifier = Modifier.size(10.dp),
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(text = time, style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

// HomeScreen - Updated untuk show semua resep dan navigate ke CategoryScreen
@Composable
fun HomeScreen(navController: NavController, foodList: List<DataResep>) {
    var searchQuery by remember { mutableStateOf("") }

    // Filter hanya berdasarkan search, tampilkan semua kategori
    val filteredFoodList = if (searchQuery.isBlank()) {
        foodList  // Tampilkan semua resep
    } else {
        foodList.filter { food ->
            food.name.contains(searchQuery, ignoreCase = true)
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TopBar(
            navController = navController,
            searchQuery = searchQuery,
            onSearchQueryChange = { searchQuery = it }
        )

        if (searchQuery.isBlank()) {
            // Banner section dengan background krem
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFD2D0A0))  // Background krem untuk area banner
            ) {
                PromoBanner()
            }

            // CategoryTabs dan FoodGrid dengan background hijau
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF5F8150))  // Background hijau untuk CategoryTabs dan FoodGrid
            ) {
                // CategoryTabs di area hijau
                CategoryTabs(
                    selectedCategory = "", // Gak ada yang selected di homepage
                    onCategorySelected = { category ->
                        // Navigate ke CategoryScreen dengan parameter kategori
                        navController.navigate(AlurApp.getCategoryRoute(category))
                    }
                )

                FoodGrid(
                    foodList = filteredFoodList,  // Semua resep atau hasil search
                    navController = navController,
                    searchQuery = searchQuery
                )
            }
        } else {
            // Search mode - background cream untuk seluruh area
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF0ECCF))  // Background cream untuk search results
            ) {
                FoodGrid(
                    foodList = filteredFoodList,  // Hasil search
                    navController = navController,
                    searchQuery = searchQuery
                )
            }
        }
    }
}

// Preview functions
@Preview(showBackground = true)
@Composable
fun PromoBannerPreview() {
    PromoBanner()
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    val navController = rememberNavController()
    TopBar(
        navController = navController,
        searchQuery = "",
        onSearchQueryChange = {}
    )
}