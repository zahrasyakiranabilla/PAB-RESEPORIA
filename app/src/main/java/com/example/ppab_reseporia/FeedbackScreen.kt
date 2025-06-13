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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

@Composable
fun FeedbackScreen(navController: NavController) {
    var feedbackText by remember { mutableStateOf("") }

    val backgroundColor = Color(0xFFD2D0A0)
    val greenColor = Color(0xFF7A977B)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(top = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.home),
                contentDescription = "Kembali",
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.CenterStart)
                    .clickable { navController.popBackStack() }
            )

            Text(
                text = "Kirim Saran",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

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
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        //Belum menerapkan button kirim saran/pengaduan
        Button(
            onClick = {
                println("Feedback sent: $feedbackText")
            },
            modifier = Modifier
                .width(180.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = greenColor,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(
                text = "Kirim",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
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