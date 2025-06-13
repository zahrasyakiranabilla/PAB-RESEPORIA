package com.example.ppab_reseporia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ppab_reseporia.ui.theme.PPABRESEPORIATheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PPABRESEPORIATheme {
                val navController = rememberNavController()
                AppNavigation(navController = navController)
            }
        }
    }
}

@Composable
fun AppNavigation(navController: androidx.navigation.NavHostController) {
    NavHost(navController = navController, startDestination = AlurApp.LOGIN_SCREEN) {
        composable(AlurApp.LOGIN_SCREEN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(AlurApp.HOME_SCREEN) {
                        popUpTo(AlurApp.LOGIN_SCREEN) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(AlurApp.HOME_SCREEN) {
            HomeScreen(navController = navController)
        }
        composable(
            route = AlurApp.CATEGORY_SCREEN,
            arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
        ) { backStackEntry ->
        val categoryName = backStackEntry.arguments?.getString("categoryName")
        CategoryScreen(categoryName = categoryName ?: "Unknown", navController = navController)
    }
        composable(
            route = AlurApp.DETAIL_RECIPE_SCREEN,
            arguments = listOf(navArgument("foodName") { type = NavType.StringType })
        ) { backStackEntry ->
        val foodName = backStackEntry.arguments?.getString("foodName")
        val selectedRecipe = allFoodList.find { it.name == foodName }
        if (selectedRecipe != null) {
            RecipeDetailScreen(recipe = selectedRecipe, onBack = { navController.popBackStack() })
        } else {
            Text("Resep tidak ditemukan!")
        }
    }
        composable(AlurApp.FAVORITE_RECIPES_SCREEN) {
            ResepFavoritScreen(navController = navController)
        }
        composable(AlurApp.PROFILE_SCREEN) {
            ProfileScreenExact(navController = navController)
        }
        composable(AlurApp.EDIT_PROFILE_SCREEN) {
            EditProfileScreen(navController = navController)
        }
        composable(AlurApp.FEEDBACK_SCREEN) {
            FeedbackScreen(navController = navController)
        }
    }
}