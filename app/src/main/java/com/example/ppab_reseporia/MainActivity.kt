package com.example.ppab_reseporia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.AccessTime
import com.example.ppab_reseporia.ui.theme.PPABRESEPORIATheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PPABRESEPORIATheme {
                HomeScreen()
            }
        }
    }
}

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD2D0A0)) // Background utama (krem/pattern)
    ) {
        TopBar()
        PromoBanner()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF5F8150)) // background hijau sesuai desain
        ) {
            CategoryTabs()
            FoodGrid()
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun TopBar() {
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
            shape = RoundedCornerShape(24.dp)
        )
        Icon(Icons.Default.Favorite, contentDescription = "Favorite")
        Icon(Icons.Default.Person, contentDescription = "Profile")
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
fun CategoryTabs() {
    val tabs = listOf("Appetizer", "Main Course", "Dessert", "Drinks")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .background(Color.White, RoundedCornerShape(24.dp))
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        tabs.forEach { tab ->
            Text(
                text = tab,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable { /* TODO: Implement action if needed */ }
            )
        }
    }
}


data class FoodData(
    val name: String,
    val imageRes: Int,
    val rating: Double,
    val likes: Int,
    val time: String
)

@Composable
fun FoodGrid() {
    val foodList = listOf(
        FoodData("Ayam Woku", R.drawable.ayamwoku, 4.7, 57, "40m"),
        FoodData("Mango Sticky", R.drawable.mangosticky, 4.5, 34, "30m"),
        FoodData("Salad Buah", R.drawable.saladbuah, 4.9, 45, "50m"),
        FoodData("Tahu Bihun", R.drawable.tahubihun, 4.2, 22, "25m"),
        FoodData("Wedang Jahe", R.drawable.wedangjahe, 4.8, 78, "45m"),
        FoodData("Pindang Serani", R.drawable.pindangserani, 4.1, 15, "20m"),
        FoodData("Tiramisu Dessert", R.drawable.tiramisudessert, 4.6, 48, "35m"),
        FoodData("Strawberry Matcha", R.drawable.strawberrymatcha, 4.3, 27, "28m"),
        FoodData("Es Kuwut", R.drawable.eskuwut, 4.7, 56, "40m"),
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxHeight()
            .padding(8.dp),
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
                time = food.time
            )
        }
    }
}

@Composable
fun FoodItem(name: String, imageRes: Int, rating: Double, likes: Int, time: String) {
    Column(
        modifier = Modifier
            .padding(4.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
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

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    PPABRESEPORIATheme {
        HomeScreen()
    }
}