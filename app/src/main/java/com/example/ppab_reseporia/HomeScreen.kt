package com.example.ppab_reseporia

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ppab_reseporia.ui.theme.PPABRESEPORIATheme

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD2D0A0))
    ) {
        TopBar(navController = navController)
        PromoBanner()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF5F8150))
        ) {
            CategoryTabs(navController = navController)
            FoodGrid(foodList = allFoodList, navController = navController)
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun CategoryTabs(navController: NavController) {
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
                    .clickable { navController.navigate(AlurApp.getCategoryRoute(tab)) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    PPABRESEPORIATheme {
        HomeScreen(navController = rememberNavController())
    }
}