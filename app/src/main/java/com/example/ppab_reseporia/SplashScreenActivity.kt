package com.example.ppab_reseporia

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ppab_reseporia.ui.theme.PPABRESEPORIATheme
import kotlinx.coroutines.delay
import com.example.ppab_reseporia.ui.theme.PoppinsFamily

class SplashScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PPABRESEPORIATheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SplashScreenContent()
                }
            }
        }
    }
}

@Composable
fun SplashScreenContent(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        delay(2000L)
        context.startActivity(Intent(context, MainActivity::class.java))
        (context as? ComponentActivity)?.finish()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF0ECCF)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logoreseporia),
            contentDescription = "Reseporia Logo",
            modifier = Modifier.size(150.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Reseporia",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = PoppinsFamily,
            color = Color(0xFF6B8A5A)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenContentPreview() {
    PPABRESEPORIATheme {
        SplashScreenContent()
    }
}