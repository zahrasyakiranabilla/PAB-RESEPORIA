package com.example.ppab_reseporia

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

// Definisi warna agar mudah diakses
private val editScreenBackgroundColor = Color(0xFFF0EFE6)
private val editScreenButtonColor = Color(0xFF73946B) // Warna hijau primer
private val editScreenTextColor = Color(0xFF333D29) // Hijau sangat tua untuk teks

@Composable
fun EditProfileScreen(navController: NavController? = null) {
    // State untuk setiap field
    var fullName by remember { mutableStateOf("Zahra Syakira Nabilla") }
    var username by remember { mutableStateOf("zahrasyakira") }
    var bio by remember { mutableStateOf("Passionate home cook, loves baking desserts.") }

    Scaffold(
        topBar = {
            EditProfileTopAppBar { navController?.popBackStack() }
        },
        containerColor = editScreenBackgroundColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()), // Membuat layar bisa di-scroll
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Composable baru untuk foto profil yang bisa diedit
            ProfilePictureEditor(
                onEditClick = { /* Logika untuk ganti foto */ }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Field untuk Full Name (contoh tambahan)
            EditableProfileField(
                value = fullName,
                onValueChange = { fullName = it },
                label = "Full Name",
                leadingIcon = Icons.Default.Person
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Field untuk Username
            EditableProfileField(
                value = username,
                onValueChange = { username = it },
                label = "Username",
                leadingIcon = Icons.Default.Person // Bisa diganti dengan ikon lain
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Field untuk Bio (multi-line)
            EditableProfileField(
                value = bio,
                onValueChange = { bio = it },
                label = "Bio",
                singleLine = false, // Atur agar bisa multi-baris
                maxLines = 4 // Batasi jumlah baris
            )

            // Spacer untuk mendorong tombol ke bawah jika konten sedikit
            Spacer(modifier = Modifier.weight(1f))

            // Composable baru untuk baris tombol aksi
            ActionButtonsRow(
                onCancelClick = { navController?.popBackStack() },
                onSaveClick = {
                    // Logika untuk menyimpan perubahan
                    println("Perubahan disimpan!")
                    navController?.popBackStack()
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

// -- Helper Composables untuk membuat UI lebih bersih --

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileTopAppBar(onBackClick: () -> Unit) {
    // GANTI "TopAppBar" menjadi "CenterAlignedTopAppBar"
    CenterAlignedTopAppBar(
        title = { Text("Edit Profile", fontWeight = FontWeight.Bold, color = editScreenTextColor) },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
    )
}
@Composable
fun ProfilePictureEditor(onEditClick: () -> Unit) {
    Box(contentAlignment = Alignment.BottomEnd) {
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .border(3.dp, editScreenButtonColor, CircleShape),
            contentScale = ContentScale.Crop
        )
        IconButton(
            onClick = onEditClick,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(editScreenButtonColor)
        ) {
            Icon(Icons.Default.CameraAlt, contentDescription = "Edit Foto", tint = Color.White)
        }
    }
}

@Composable
fun EditableProfileField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: ImageVector? = null,
    singleLine: Boolean = true,
    maxLines: Int = 1
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = singleLine,
        maxLines = maxLines,
        textStyle = TextStyle(color = editScreenTextColor, fontSize = 16.sp),
        leadingIcon = {
            if (leadingIcon != null) {
                Icon(leadingIcon, contentDescription = label, tint = editScreenTextColor.copy(alpha = 0.7f))
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = editScreenButtonColor,
            unfocusedBorderColor = Color.Gray,
            cursorColor = editScreenButtonColor,
            focusedLabelColor = editScreenButtonColor,
        )
    )
}

@Composable
fun ActionButtonsRow(onCancelClick: () -> Unit, onSaveClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Tombol Batal (sekunder)
        OutlinedButton(
            onClick = onCancelClick,
            modifier = Modifier
                .weight(1f)
                .height(50.dp),
            shape = RoundedCornerShape(50),
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            Text("Batal", color = Color.Gray)
        }
        // Tombol Simpan (primer)
        Button(
            onClick = onSaveClick,
            modifier = Modifier
                .weight(1f)
                .height(50.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = editScreenButtonColor)
        ) {
            Text("Simpan Perubahan")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun EditProfileScreenPreview() {
    MaterialTheme {
        EditProfileScreen(rememberNavController())
    }
}