package com.example.ppab_reseporia

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

val editScreenBackgroundColor = Color(0xFFF0EFE6)
val editScreenIconBackgroundColor = Color(0xFFE0E5D6)
val editScreenButtonColor = Color(0xFF8F9D7F)
val editScreenTextColor = Color(0xFF333333)
val editScreenLineColor = Color.Black

@Composable
fun EditProfileScreen(navController: NavController? = null) {
    // masih menggunakan username dan email dummy
    var username by remember { mutableStateOf("Zahra Syakira Nabilla") }
    var email by remember { mutableStateOf("zahrasyakiranabilla@gmail.com") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(editScreenBackgroundColor)
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.panah),
                contentDescription = "Kembali",
                modifier = Modifier
                    .size(28.dp)
                    .align(Alignment.CenterStart)
                    .clickable { navController?.popBackStack() }
            )

            Text(
                text = "Edit Profile",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = editScreenTextColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Box(
            modifier = Modifier
                .size(160.dp)
                .clip(CircleShape)
                .background(editScreenIconBackgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile picture placeholder",
                modifier = Modifier.size(108.dp),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        EditableProfileField(
            label = "Username",
            value = username,
            onValueChange = { username = it }
        )
        Spacer(modifier = Modifier.height(24.dp))

        EditableProfileField(
            label = "Email",
            value = email,
            onValueChange = { email = it },
            keyboardType = KeyboardType.Email
        )
        Spacer(modifier = Modifier.height(40.dp))

        ActionButton(
            text = "Simpan Perubahan",
            onClick = {
                navController?.popBackStack()
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        ActionButton(
            text = "Batal",
            onClick = { navController?.popBackStack() }
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun EditableProfileField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                color = editScreenTextColor,
                fontSize = 16.sp
            )
        },
        modifier = Modifier.fillMaxWidth(0.85f),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = true,
        textStyle = TextStyle(color = editScreenTextColor, fontSize = 16.sp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = editScreenLineColor,
            unfocusedIndicatorColor = editScreenLineColor.copy(alpha = 0.7f),
            cursorColor = editScreenTextColor,
            focusedLabelColor = editScreenTextColor,
            unfocusedLabelColor = editScreenTextColor.copy(alpha = 0.7f)
        )
    )
}

@Composable
fun ActionButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(0.75f)
            .height(50.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = editScreenButtonColor,
            contentColor = Color.White
        )
    ) {
        Text(text = text, fontSize = 16.sp, fontWeight = FontWeight.Medium)
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EFE6)
@Composable
fun EditProfileScreenPreview() {
    MaterialTheme {
        EditProfileScreen(navController = null)
    }
}