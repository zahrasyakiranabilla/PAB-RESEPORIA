package com.example.ppab_reseporia

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun ProfileScreenExact(navController: NavController? = null) {
    val fullName = "Zahra Syakira Nabilla"
    val email = "zahrasyakiranabilla@gmail.com"

    // State untuk mengontrol dialog
    var showLogoutDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0EFE6)) // Warna latar belakang utama
    ) {
        ProfileTopAppBar(navController)
        ProfileHeader(fullName, email) {
            navController?.navigate(AlurApp.EDIT_PROFILE_SCREEN)
        }
        Spacer(modifier = Modifier.height(24.dp))
        StatisticsSection(savedRecipes = 9)
        Spacer(modifier = Modifier.height(16.dp))
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            ProfileMenuItem(
                icon = Icons.Default.FavoriteBorder,
                text = "Resep Favorit",
                onClick = { navController?.navigate(AlurApp.FAVORITE_RECIPES_SCREEN) }
            )
            Divider()
            ProfileMenuItem(
                icon = Icons.AutoMirrored.Filled.HelpOutline,
                text = "Saran & Pengaduan",
                onClick = { navController?.navigate(AlurApp.FEEDBACK_SCREEN) }
            )
        }
        Spacer(modifier = Modifier.weight(1f))

        // Tombol Log Out sekarang hanya menampilkan dialog
        LogoutButton {
            showLogoutDialog = true
        }
    }

    // Panggil dialog bertema jika state-nya true
    if (showLogoutDialog) {
        ThemedLogoutDialog(
            onDismiss = { showLogoutDialog = false },
            onConfirmLogout = {
                showLogoutDialog = false
                navController?.navigate(AlurApp.LOGIN_SCREEN) {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            }
        )
    }
}

// Composable baru untuk dialog yang sudah disesuaikan dengan tema
@Composable
fun ThemedLogoutDialog(
    onConfirmLogout: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(20.dp),
        containerColor = Color(0xFFEAE7C6),
        icon = { Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Log Out Icon", tint = Color(0xFF73946B)) },
        title = {
            Text(
                text = "Confirm Log Out",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333D29)
            )
        },
        text = {
            Text(
                text = "Are you sure you want to log out from Reseporia?",
                color = Color(0xFF333D29).copy(alpha = 0.8f)
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirmLogout,
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFC62828),
                    contentColor = Color.White
                )
            ) {
                Text("Log Out")
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(50),
                border = BorderStroke(1.dp, Color.Gray)
            ) {
                Text("Cancel", color = Color.Gray)
            }
        }
    )
}


// Semua Composable lainnya tidak perlu diubah
@Composable
fun ProfileTopAppBar(navController: NavController?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Kembali",
            modifier = Modifier
                .size(28.dp)
                .clickable { navController?.popBackStack() }
        )
        Text(
            text = "Profile",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(28.dp))
    }
}

@Composable
fun ProfileHeader(fullName: String, email: String, onEditClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
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
        Text(text = fullName, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = email, fontSize = 14.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onEditClick,
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF73946B))
        ) {
            Text("Edit Profile", modifier = Modifier.padding(horizontal = 16.dp))
        }
    }
}

@Composable
fun StatisticsSection(savedRecipes: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StatItem(number = savedRecipes, label = "Resep Disimpan")
    }
}

@Composable
fun StatItem(number: Int, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = number.toString(), fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text(text = label, fontSize = 12.sp, color = Color.Gray)
    }
}

@Composable
fun ProfileMenuItem(icon: ImageVector, text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = text, modifier = Modifier.size(24.dp), tint = Color(0xFF73946B))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, modifier = Modifier.weight(1f), fontSize = 16.sp)
        Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.Gray)
    }
}

@Composable
fun LogoutButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
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

@Preview(showBackground = true)
@Composable
fun ProfileScreenExactPreview() {
    MaterialTheme {
        ProfileScreenExact(navController = rememberNavController())
    }
}