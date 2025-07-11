package com.example.ppab_reseporia

object AlurApp {
    const val SPLASH_SCREEN = "splash"
    const val LOGIN_SCREEN = "login"
    const val REGISTER_SCREEN = "register"
    const val HOME_SCREEN = "home"
    const val CATEGORY_SCREEN = "category/{categoryName}"
    const val DETAIL_RECIPE_SCREEN = "detailRecipe/{foodName}"
    const val FAVORITE_RECIPES_SCREEN = "favoriteRecipes"
    const val PROFILE_SCREEN = "profile"
    const val EDIT_PROFILE_SCREEN = "editProfile"
    const val FEEDBACK_SCREEN = "feedback"
    const val WELCOME_SCREEN = "welcome/{userName}"

    fun getCategoryRoute(categoryName: String): String {
        return "category/$categoryName"
    }

    fun getDetailRecipeRoute(foodName: String): String {
        return "detailRecipe/$foodName"
    }

    fun getWelcomeRoute(userName: String): String {
        return "welcome/$userName"
    }
}


