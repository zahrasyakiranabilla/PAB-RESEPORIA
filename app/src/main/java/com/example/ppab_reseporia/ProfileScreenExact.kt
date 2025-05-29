package com.example.ppab_reseporia

import android.util.Log
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ProfileScreenExact(navController: NavController? = null) {
    //masih menggunakan data dummy
    val username = "Zahra Syakira Nabilla"
    val email = "zahrasyakiranabilla@gmail.com"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD2D0A0))
            .padding(top = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.home),
                contentDescription = "Kembali ke Beranda",
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.CenterStart)
                    .clickable { navController?.popBackStack() }
            )
            Text(
                text = "Profile",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF73946B))
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(108.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFC5D0B3).copy(alpha = 0.3f)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(20.dp))
                ProfileDisplayField(label = "Username", value = username)
                Spacer(modifier = Modifier.height(12.dp))
                ProfileDisplayField(label = "Email", value = email)
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {
                        Log.d("ProfileScreenNav", "Tombol Edit Profile diklik.")
                        if (navController == null) {
                            Log.e("ProfileScreenNav", "NavController adalah null!")
                        } else {
                            Log.d("ProfileScreenNav", "Mencoba navigasi ke 'EditProfile'...")
                            navController.navigate(AlurApp.EDIT_PROFILE_SCREEN)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(48.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC5D0B3))
                ) {
                    Text("Edit Profile", color = Color.Black, fontSize = 16.sp)
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ProfileActionButton(
                text = "Resep Favorite",
                onClick = {
                    navController?.navigate(AlurApp.FAVORITE_RECIPES_SCREEN)
                },
                modifier = Modifier.weight(1f)
            )
            ProfileActionButton(
                text = "Saran/Pengaduan",
                onClick = {
                    navController?.navigate(AlurApp.FEEDBACK_SCREEN)
                },
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ProfileDisplayField(label: String, value: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFC5D0B3), RoundedCornerShape(10.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "$label : ",
                color = Color.Black,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = value,
                color = Color.Black,
                fontSize = 15.sp
            )
        }
    }
}

@Composable
fun ProfileActionButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier.height(55.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF73946B))
    ) {
        Text(text, color = Color.White, fontSize = 15.sp, textAlign = TextAlign.Center)
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFD2D0A0)
@Composable
fun ProfileScreenExactPreview() {
    MaterialTheme {
        ProfileScreenExact(navController = null)
    }
}