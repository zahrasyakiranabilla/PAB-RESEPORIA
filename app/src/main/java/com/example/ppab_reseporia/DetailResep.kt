package com.example.ppab_reseporia

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun RecipeDetailPreview() {
    // Untuk preview menggunakan resep pertama di allFoodList
    val sampleRecipe = allFoodList.first()
    RecipeDetailScreen(recipe = sampleRecipe, onBack = {})
}

@Composable
fun RecipeDetailScreen(recipe: DataResep, onBack: () -> Unit) {
    val scrollState = rememberScrollState()
    var comment by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            // Top Bar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE8E6CE))
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .clickable { onBack() }
                        .size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(recipe.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(scrollState)
                    .background(Color(0xFFE8E6CE))
            ) {
                Card(
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Image(
                        painter = painterResource(id = recipe.imageRes),
                        contentDescription = "Makanan",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Fit
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Tentang Makanan
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF98BC92))
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Tentang Makanan",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = recipe.about,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Bahan-Bahan
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text("Bahan-Bahan", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    recipe.ingredients.forEach { ingredient -> Text("• $ingredient")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Cara Membuat
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text("Cara Membuat", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    recipe.instructions.forEachIndexed { index, instruction -> Text("${index + 1}. $instruction")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Video Placeholder (BELUM MENGGUNAKAN VIDEO)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(Color.LightGray, RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Video Resep", color = Color.DarkGray)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Ulasan
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF5A7F65))
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Ulasan",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = comment,
                        onValueChange = { comment = it },
                        placeholder = { Text("Tulis komentar di sini") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, RoundedCornerShape(8.dp))
                    )
                }

                // Simpan Resep Button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                            // Belum menerapkan simpan resep ke favorite
                        },
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5A7F65))
                    ) {
                        Text("Simpan Resep", color = Color.White)
                    }
                }
            }
        }
    )
}