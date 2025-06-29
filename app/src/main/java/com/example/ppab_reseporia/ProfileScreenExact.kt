package com.example.ppab_reseporia

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.runtime.*
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
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowRight

@Composable
fun ProfileScreenExact(navController: NavController) {
    val context = LocalContext.current
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }

    // Load favorites dan track jumlah resep favorit
    LaunchedEffect(Unit) {
        // Load favorites from storage
        FavoritesManager.loadFavorites(context)

        // Load user profile data
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null) {
            FirebaseFirestore.getInstance().collection("users").document(uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        fullName = document.getString("fullName") ?: ""
                        email = document.getString("email") ?: ""
                        bio = document.getString("bio") ?: ""
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("Firestore", "❌ Gagal ambil data user: ${e.message}")
                }
        }
    }

    // Get jumlah resep favorit secara real-time
    val favoriteCount = FavoritesManager.favoriteRecipes.size

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF0ECCF)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 72.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfileHeader(
                    fullName = fullName,
                    email = email,
                    bio = bio,
                    onEditClick = {
                        navController.navigate(AlurApp.EDIT_PROFILE_SCREEN)
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Stats section - Resep Disimpan (Real-time dari FavoritesManager)
                Text(
                    text = favoriteCount.toString(),
                    fontSize = 29.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "Resep Disimpan",
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Menu Items
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) {
                    // Resep Favorit dengan badge count
                    MenuItemRowWithBadge(
                        icon = Icons.Default.Favorite,
                        title = "Resep Favorit",
                        badgeCount = favoriteCount,
                        onClick = { navController.navigate(AlurApp.FAVORITE_RECIPES_SCREEN) }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Saran & Pengaduan
                    MenuItemRow(
                        icon = Icons.Default.Info,
                        title = "Saran & Pengaduan",
                        onClick = { navController.navigate(AlurApp.FEEDBACK_SCREEN) }
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                LogoutButton(navController)
            }

            // Top Bar dengan tombol back
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                color = Color.Transparent
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    IconButton(
                        onClick = { navController.navigate(AlurApp.HOME_SCREEN) },
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                    Text(
                        text = "Profile",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
fun MenuItemRowWithBadge(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    badgeCount: Int,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Black
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = title,
                    fontSize = 16.sp,
                    color = Color.Black
                )

                // Badge untuk menampilkan jumlah favorit
                if (badgeCount > 0) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(
                        modifier = Modifier.size(20.dp),
                        shape = CircleShape,
                        color = Color(0xFF5F8150)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (badgeCount > 99) "99+" else badgeCount.toString(),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun MenuItemRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Black
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = title,
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun ProfileHeader(fullName: String, email: String, bio: String, onEditClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(110.dp)
                .clip(CircleShape)
                .border(2.dp, Color(0xFF73946B), CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = fullName,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = email,
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = bio,
            fontSize = 14.sp,
            color = Color.DarkGray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = onEditClick,
            shape = RoundedCornerShape(50),
            colors = buttonColors(containerColor = Color(0xFF73946B))
        ) {
            Text("Edit Profile", modifier = Modifier.padding(horizontal = 16.dp))
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun LogoutButton(navController: NavController) {
    Button(
        onClick = {
            FirebaseAuth.getInstance().signOut()
            navController.navigate(AlurApp.LOGIN_SCREEN) {
                popUpTo(0) { inclusive = true }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.error
        ),
        elevation = null
    ) {
        Icon(imageVector = Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Log Out")
        Spacer(modifier = Modifier.width(8.dp))
        Text("Log Out", fontWeight = FontWeight.Bold)
    }
}