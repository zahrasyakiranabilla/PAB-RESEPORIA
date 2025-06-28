package com.example.ppab_reseporia

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

private val editScreenBackgroundColor = Color(0xFFF0ECCF)
private val editScreenButtonColor = Color(0xFF73946B)
private val editScreenTextColor = Color(0xFF333D29)

@Composable
fun EditProfileScreen(navController: NavController?) {
    val viewModel: EditProfileViewModel = viewModel()
    val fullName = viewModel.fullName
    val username = viewModel.username
    val bio = viewModel.bio
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadUserProfile()
    }

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
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            ProfilePictureEditor(
                onEditClick = { /* Logika untuk ganti foto */ }
            )

            Spacer(modifier = Modifier.height(32.dp))

            EditableProfileField(
                value = fullName,
                onValueChange = { viewModel.fullName = it },
                label = "Full Name",
                leadingIcon = Icons.Default.Person
            )
            Spacer(modifier = Modifier.height(16.dp))

            EditableProfileField(
                value = username,
                onValueChange = { viewModel.username = it },
                label = "Username",
                leadingIcon = Icons.Default.Person
            )
            Spacer(modifier = Modifier.height(16.dp))

            EditableProfileField(
                value = bio,
                onValueChange = { viewModel.bio = it },
                label = "Bio",
                singleLine = false,
                maxLines = 4
            )

            Spacer(modifier = Modifier.weight(1f))

            ActionButtonsRow(
                onCancelClick = { navController?.popBackStack() },
                onSaveClick = {
                    viewModel.saveChanges {
                        Toast.makeText(context, "Perubahan berhasil disimpan", Toast.LENGTH_SHORT).show()
                        navController?.popBackStack()
                    }
                },
                isLoading = viewModel.loading
            )
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
fun ActionButtonsRow(
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit,
    isLoading: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Tombol Batal (sekunder)
        OutlinedButton(
            onClick = onCancelClick,
            enabled = !isLoading, // ⛔ disable saat loading
            modifier = Modifier
                .weight(1f)
                .height(55.dp),
            shape = RoundedCornerShape(50),
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            Text("Batal", color = Color.Gray)
        }

        // Tombol Simpan (primer)
        Button(
            onClick = onSaveClick,
            enabled = !isLoading, // ⛔ disable saat loading
            modifier = Modifier
                .weight(1f)
                .height(55.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = editScreenButtonColor)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = "Simpan Perubahan",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
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