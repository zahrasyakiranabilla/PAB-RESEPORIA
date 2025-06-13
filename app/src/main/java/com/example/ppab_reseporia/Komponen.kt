package com.example.ppab_reseporia

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun TopBar(navController: NavController) {
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
            modifier = Modifier.size(40.dp)
        )
        TextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Search...") },
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            shape = RoundedCornerShape(24.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent
        )
        )
        Icon(
            Icons.Default.Favorite,
            contentDescription = "Favorite",
            tint = Color.White,
            modifier = Modifier.clickable { navController.navigate(AlurApp.FAVORITE_RECIPES_SCREEN) }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            Icons.Default.Person,
            contentDescription = "Profile",
            tint = Color.White,
            modifier = Modifier.clickable { navController.navigate(AlurApp.PROFILE_SCREEN) }
        )
    }
}

@Composable
fun PromoBanner() {
    Image(
        painter = painterResource(R.drawable.banner1),
        contentDescription = "bannerpromo",
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(8.dp),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun FoodGrid(foodList: List<DataResep>, navController: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxHeight()
            .padding(8.dp)
            .background(Color(0xFF5F8150)),
        contentPadding = PaddingValues(4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        itemsIndexed(foodList) { _, food ->
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