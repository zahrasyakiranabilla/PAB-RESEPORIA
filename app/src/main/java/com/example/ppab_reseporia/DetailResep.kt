package com.example.ppab_reseporia

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import android.webkit.WebView
import android.webkit.WebViewClient
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DetailTopBar(
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
        Row(verticalAlignment = Alignment.CenterVertically) {
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
                        navController.navigate(AlurApp.HOME_SCREEN) {
                            popUpTo(AlurApp.HOME_SCREEN) { inclusive = true }
                        }
                    }
            )
        }

        TextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            placeholder = { Text("Search...", color = Color.Gray) },
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
                Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Gray)
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { onSearchQueryChange("") }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear", tint = Color.Gray)
                    }
                }
            },
            singleLine = true
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
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
}

@Composable
fun StarRating(
    rating: Int,
    onRatingChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    isReadOnly: Boolean = false
) {
    Row(modifier = modifier) {
        for (i in 1..5) {
            Icon(
                imageVector = if (i <= rating) Icons.Filled.Star else Icons.Outlined.Star,
                contentDescription = "Star $i",
                tint = if (i <= rating) Color(0xFFFFD700) else Color.Gray,
                modifier = Modifier
                    .size(if (isReadOnly) 16.dp else 32.dp)
                    .clickable { if (!isReadOnly) onRatingChange(i) }
            )
        }
    }
}

@Composable
fun FirebaseRatingDisplay(
    averageRating: Double,
    totalReviews: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        StarRating(
            rating = averageRating.toInt(),
            onRatingChange = {},
            isReadOnly = true
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = String.format(Locale.getDefault(), "%.1f", averageRating),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Text(
            text = " ($totalReviews ${if (totalReviews == 1) "ulasan" else "ulasan"})",
            color = Color.Gray,
            fontSize = 14.sp
        )
    }
}

@Composable
fun FirebaseReviewItem(
    review: FirebaseReview,
    onHelpful: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        modifier = Modifier.size(32.dp),
                        shape = CircleShape,
                        color = Color(0xFF5F8150)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = review.userName.firstOrNull()?.uppercase() ?: "U",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = review.userName,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                        Text(
                            text = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                                .format(Date(review.timestamp)),
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                    }
                }
                StarRating(
                    rating = review.rating,
                    onRatingChange = {},
                    isReadOnly = true
                )
            }

            if (review.comment.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = review.comment,
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
            }

            // Helpful button
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { onHelpful(review.id) },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        Icons.Default.ThumbUp,
                        contentDescription = "Helpful",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Text(
                    text = if (review.helpful > 0) "${review.helpful} helpful" else "Helpful?",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun YouTubePlayer(
    videoId: String,
    modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
            }
        },
        update = { webView ->
            val embedUrl = "https://www.youtube.com/embed/$videoId"
            webView.loadUrl(embedUrl)
        },
        modifier = modifier
    )
}

@Composable
fun RecipeDetailScreen(
    recipe: DataResep,
    navController: NavController,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    var comment by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }
    var userRating by remember { mutableIntStateOf(0) }
    var isSubmitting by remember { mutableStateOf(false) }

    // Track favorite status
    var isFavorite by remember { mutableStateOf(FavoritesManager.isFavorite(recipe)) }

    // Firebase review state
    var recipeRating by remember { mutableStateOf(FirebaseRecipeRating()) }
    var reviews by remember { mutableStateOf<List<FirebaseReview>>(emptyList()) }
    var existingUserReview by remember { mutableStateOf<FirebaseReview?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    // Current user info
    val currentUser = FirebaseAuth.getInstance().currentUser
    val currentUserId = currentUser?.uid ?: ""
    val currentUserName = currentUser?.displayName ?: currentUser?.email?.substringBefore("@") ?: "Anonymous"
    val currentUserEmail = currentUser?.email ?: ""

    // Load Firebase data
    LaunchedEffect(recipe.name) {
        if (!isSubmitting) {
            scope.launch {
                isLoading = true
                try {
                    val loadedReviews = FirebaseReviewManager.getReviewsForRecipe(recipe.name)
                    reviews = loadedReviews

                    val loadedRating = FirebaseReviewManager.getRecipeRating(recipe.name)
                    recipeRating = loadedRating

                    val userReview = FirebaseReviewManager.getUserReviewForRecipe(recipe.name, currentUserId)
                    existingUserReview = userReview
                } catch (e: Exception) {
                    Toast.makeText(context, "Error loading reviews: ${e.message}", Toast.LENGTH_SHORT).show()
                } finally {
                    isLoading = false
                }
            }
        }
    }

    // Real-time refresh when reviews.size changes
    LaunchedEffect(reviews.size) {
        if (reviews.isNotEmpty()) {
            scope.launch {
                try {
                    if (recipeRating.totalReviews != reviews.size && !isSubmitting) {
                        val freshRating = FirebaseReviewManager.getRecipeRating(recipe.name)

                        if (freshRating.totalReviews >= reviews.size) {
                            recipeRating = freshRating
                        }
                    }
                } catch (e: Exception) {
                    // Handle error silently
                }
            }
        }
    }

    Scaffold(
        topBar = {
            DetailTopBar(
                navController = navController,
                searchQuery = searchQuery,
                onSearchQueryChange = { searchQuery = it },
                onBack = onBack
            )
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
                        .padding(top = 16.dp)
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

                // Recipe Title
                Text(
                    text = recipe.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Center
                )

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
                        fontSize = 18.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        textAlign = TextAlign.Center
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
                    recipe.ingredients.forEach { ingredient ->
                        Text("• $ingredient")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Cara Membuat
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text("Cara Membuat", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    recipe.instructions.forEachIndexed { index, instruction ->
                        Text("${index + 1}. $instruction")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // YouTube Video Player
                Column(modifier = Modifier.padding(horizontal = 32.dp)) {
                    Text(
                        text = "Video Tutorial",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        YouTubePlayer(
                            videoId = recipe.videoId ?: "dQw4w9WgXcQ",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Firebase Review Form
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF5A7F65))
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Berikan Ulasan",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Rating:",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    StarRating(
                        rating = userRating,
                        onRatingChange = { userRating = it }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Komentar:",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = comment,
                        onValueChange = { comment = it },
                        placeholder = { Text("Tulis komentar tentang resep ini...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        maxLines = 3
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            onClick = {
                                scope.launch {
                                    if (userRating > 0 && comment.isNotBlank()) {
                                        isSubmitting = true

                                        try {
                                            val reviewData = FirebaseReview(
                                                id = existingUserReview?.id ?: "",
                                                recipeId = recipe.name,
                                                userId = currentUserId,
                                                userName = currentUserName,
                                                userEmail = currentUserEmail,
                                                rating = userRating,
                                                comment = comment,
                                                timestamp = System.currentTimeMillis()
                                            )

                                            val result = FirebaseReviewManager.addReview(reviewData)

                                            if (result.isSuccess) {
                                                val savedReviewId = result.getOrNull() ?: reviewData.id
                                                val newReview = reviewData.copy(id = savedReviewId)

                                                existingUserReview = null

                                                // Reset form after successful submission
                                                userRating = 0
                                                comment = ""

                                                // Immediate UI update
                                                val updatedReviews = listOf(newReview) + reviews.filter { it.userId != currentUserId }
                                                reviews = updatedReviews

                                                // Update rating immediately
                                                val totalRating = updatedReviews.sumOf { it.rating }
                                                val avgRating = if (updatedReviews.isNotEmpty()) totalRating.toDouble() / updatedReviews.size else 0.0
                                                recipeRating = FirebaseRecipeRating(
                                                    recipeId = recipe.name,
                                                    averageRating = avgRating,
                                                    totalReviews = updatedReviews.size
                                                )

                                                Toast.makeText(
                                                    context,
                                                    "Ulasan berhasil dikirim!",
                                                    Toast.LENGTH_SHORT
                                                ).show()

                                                // Background refresh to sync with Firebase
                                                scope.launch {
                                                    try {
                                                        kotlinx.coroutines.delay(3000)
                                                        val freshReviews = FirebaseReviewManager.getReviewsForRecipe(recipe.name)
                                                        val freshRating = FirebaseReviewManager.getRecipeRating(recipe.name)

                                                        if (freshReviews.size >= updatedReviews.size) {
                                                            reviews = freshReviews
                                                            recipeRating = freshRating
                                                        }
                                                    } catch (e: Exception) {
                                                        // Handle error silently
                                                    }
                                                }
                                            } else {
                                                Toast.makeText(
                                                    context,
                                                    "Gagal menyimpan ulasan: ${result.exceptionOrNull()?.message}",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        } catch (e: Exception) {
                                            Toast.makeText(
                                                context,
                                                "Error: ${e.message}",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        } finally {
                                            isSubmitting = false
                                        }
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Mohon berikan rating dan komentar",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF98BC92)
                            ),
                            shape = RoundedCornerShape(8.dp),
                            enabled = userRating > 0 && comment.isNotBlank() && !isSubmitting
                        ) {
                            if (isSubmitting) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(16.dp),
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                            Text(
                                text = if (isSubmitting) {
                                    "Menyimpan..."
                                } else {
                                    "Kirim Ulasan"
                                },
                                color = Color.White
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Firebase Rating Display
                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color(0xFF5F8150)
                        )
                    }
                } else if (recipeRating.totalReviews > 0) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        FirebaseRatingDisplay(
                            averageRating = recipeRating.averageRating,
                            totalReviews = recipeRating.totalReviews
                        )
                    }
                }

                // Display Firebase Reviews
                if (reviews.isNotEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = "Ulasan Pengguna (${reviews.size})",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        reviews.forEach { reviewItem ->
                            FirebaseReviewItem(
                                review = reviewItem,
                                onHelpful = { reviewId ->
                                    scope.launch {
                                        FirebaseReviewManager.markReviewHelpful(reviewId)
                                        Toast.makeText(context, "Terima kasih atas feedback Anda!", Toast.LENGTH_SHORT).show()

                                        // Update helpful count in local list
                                        reviews = reviews.map { review ->
                                            if (review.id == reviewId) {
                                                review.copy(helpful = review.helpful + 1)
                                            } else {
                                                review
                                            }
                                        }
                                    }
                                },
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
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
                            FavoritesManager.toggleFavorite(context, recipe)
                            isFavorite = FavoritesManager.isFavorite(recipe)

                            val message = if (isFavorite) {
                                "Resep ditambahkan ke favorit!"
                            } else {
                                "Resep dihapus dari favorit!"
                            }

                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        },
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isFavorite) Color(0xFFE74C3C) else Color(0xFF5A7F65)
                        )
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isFavorite) "Hapus dari Favorit" else "Simpan ke Favorit",
                            color = Color.White
                        )
                    }
                }
            }
        }
    )
}