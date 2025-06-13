package com.example.ppab_reseporia

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ppab_reseporia.ui.theme.PPABRESEPORIATheme

val GreenBackground = Color(0xFF73946B)
val LightBeigeBackground = Color(0xFFD2D0A0)
val TextColorInsideCard = Color.White
val ButtonTextColor = Color(0xFF000000)
val TextFieldLineColor = Color.White

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    loginViewModel: LoginViewModel = viewModel()
) {

    val username = loginViewModel.username
    val email = loginViewModel.email
    val password = loginViewModel.password
    val shakeButtonTrigger = loginViewModel.shakeButtonTrigger

    val fieldsNotEmpty = username.isNotBlank() && email.isNotBlank() && password.isNotBlank()

    val buttonOffsetX = remember { Animatable(0f) }

    LaunchedEffect(shakeButtonTrigger) {
        if (shakeButtonTrigger > 0) {
            buttonOffsetX.snapTo(0f)
            buttonOffsetX.animateTo(
                targetValue = 0f,
                animationSpec = keyframes {
                    durationMillis = 500
                    val bounce = 8f
                    val lessBounce = 5f
                    0f at 0
                    -bounce at 80
                    bounce at 160
                    -lessBounce at 240
                    lessBounce at 320
                    -bounce / 2 at 400
                    bounce / 2 at 450
                    0f at durationMillis
                }
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBeigeBackground),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
        ) {

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = GreenBackground),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(32.dp)
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logoreseporia),
                        contentDescription = "Logo Reseporia",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(50)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Reseporia",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextColorInsideCard,
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    LoginTextField(
                        value = username,
                        onValueChange = { loginViewModel.updateUsername(it) },
                        labelText = "Username",
                        placeholderText = "Masukkan username",
                        fontWeight = FontWeight.Light
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    LoginTextField(
                        value = email,
                        onValueChange = { loginViewModel.updateEmail(it) },
                        labelText = "Email",
                        placeholderText = "email@contoh.com",
                        keyboardType = KeyboardType.Email,
                        fontWeight = FontWeight.Light
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    LoginTextField(
                        value = password,
                        onValueChange = { loginViewModel.updatePassword(it) },
                        labelText = "Password",
                        placeholderText = "Masukkan password",
                        keyboardType = KeyboardType.Password,
                        isPassword = true,
                        fontWeight = FontWeight.Light
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = {
                            loginViewModel.login(onLoginSuccess)
                        },
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (fieldsNotEmpty) Color.White else Color.White.copy(alpha = 0.7f),
                            disabledContainerColor = Color.White.copy(alpha = 0.5f),
                            contentColor = ButtonTextColor,
                            disabledContentColor = ButtonTextColor.copy(alpha = 0.5f)
                        ),
                        enabled = true,
                        modifier = Modifier
                            .padding(horizontal = 32.dp)
                            .offset(x = buttonOffsetX.value.dp)
                    ) {
                        Text(
                            text = "Masuk",
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LoginTextField(
    value: String,
    onValueChange: (String) -> Unit,
    labelText: String,
    placeholderText: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false,
    fontWeight: FontWeight? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text(
                text = labelText,
                fontWeight = fontWeight
            )
        },
        placeholder = { Text(placeholderText, color = TextColorInsideCard.copy(alpha = 0.7f)) },
        textStyle = TextStyle(color = TextColorInsideCard, fontSize = 16.sp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = TextFieldLineColor,
            unfocusedIndicatorColor = TextFieldLineColor.copy(alpha = 0.7f),
            disabledIndicatorColor = TextFieldLineColor.copy(alpha = 0.3f),
            cursorColor = TextColorInsideCard,
            focusedLabelColor = TextColorInsideCard,
            unfocusedLabelColor = TextColorInsideCard.copy(alpha = 0.7f)
        )
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFE8E6D8)
@Composable
fun LoginScreenPreview() {
    PPABRESEPORIATheme {
        LoginScreen(onLoginSuccess = {})
    }
}