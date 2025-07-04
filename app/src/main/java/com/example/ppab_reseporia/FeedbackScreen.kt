package com.example.ppab_reseporia

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.ppab_reseporia.ui.theme.PPABRESEPORIATheme
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Data class untuk feedback
data class Feedback(
    val id: String = "",
    val message: String = "",
    val timestamp: String = "",
    val status: String = "pending" // pending, read, responded
)

@Composable
fun FeedbackTopBar(
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
fun FeedbackScreen(navController: NavController) {
    var feedbackText by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }

    val backgroundColor = Color(0xFFF0ECCF)
    val greenColor = Color(0xFF7A977B)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // Firebase Firestore instance
    val db = Firebase.firestore

    // Function untuk submit feedback ke Firebase
    suspend fun submitFeedback(message: String): Boolean {
        return try {
            val currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
            val feedbackId = db.collection("feedbacks").document().id

            val feedback = Feedback(
                id = feedbackId,
                message = message,
                timestamp = currentTime,
                status = "pending"
            )

            db.collection("feedbacks")
                .document(feedbackId)
                .set(feedback)
                .await()

            true
        } catch (e: Exception) {
            println("Error submitting feedback: ${e.message}")
            false
        }
    }

    // Show success message
    LaunchedEffect(showSuccess) {
        if (showSuccess) {
            snackbarHostState.showSnackbar("Saran berhasil dikirim! Terima kasih atas masukan Anda.")
            showSuccess = false
        }
    }

    Scaffold(
        topBar = {
            FeedbackTopBar(
                navController = navController,
                searchQuery = searchQuery,
                onSearchQueryChange = { searchQuery = it },
                onBack = { navController.popBackStack() }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(backgroundColor)
                .padding(top = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title di content area
            Text(
                text = "Kirim Saran",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Kami dari Reseporia selalu berusaha menyajikan resep terbaik untuk Anda, namun kami sadar masih banyak hal yang bisa ditingkatkan. Punya ide, kritik, atau saran? Kami sangat menghargai setiap masukan yang Anda berikan untuk membantu menjadikan Reseporia lebih baik lagi.",
                fontSize = 14.sp,
                color = Color.Black,
                textAlign = TextAlign.Justify,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = feedbackText,
                onValueChange = { feedbackText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(horizontal = 35.dp),
                textStyle = LocalTextStyle.current.copy(color = Color.White),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    cursorColor = Color.White,
                    focusedContainerColor = greenColor,
                    unfocusedContainerColor = greenColor,
                    disabledContainerColor = greenColor,
                    errorContainerColor = greenColor
                ),
                shape = RoundedCornerShape(12.dp),
                singleLine = false,
                placeholder = {
                    Text(
                        text = "Tulis saran Anda di sini...",
                        color = Color.White.copy(alpha = 0.7f)
                    )
                },
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Button kirim saran dengan Firebase integration
            Button(
                onClick = {
                    if (feedbackText.trim().isNotEmpty()) {
                        scope.launch {
                            isLoading = true
                            val success = submitFeedback(feedbackText.trim())
                            isLoading = false

                            if (success) {
                                feedbackText = "" // Clear form
                                showSuccess = true
                            } else {
                                snackbarHostState.showSnackbar("Gagal mengirim saran. Silakan coba lagi.")
                            }
                        }
                    } else {
                        scope.launch {
                            snackbarHostState.showSnackbar("Mohon isi saran terlebih dahulu.")
                        }
                    }
                },
                modifier = Modifier
                    .width(180.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = greenColor,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(24.dp),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Text(
                        text = "Kirim",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFeedbackScreen() {
    PPABRESEPORIATheme {
        FeedbackScreen(navController = rememberNavController())
    }
}