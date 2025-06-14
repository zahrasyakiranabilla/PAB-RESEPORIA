package com.example.ppab_reseporia

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ppab_reseporia.ui.theme.PoppinsFamily

@Composable
fun RegisterScreen(navController: NavController) {
    val registerViewModel: RegisterViewModel = viewModel()

    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.White, fontSize = 14.sp)) {
            append("Already have an account? ")
        }
        pushStringAnnotation(tag = "LOGIN", annotation = "LOGIN")
        withStyle(style = SpanStyle(color = Color(0xFFD2D0A0), fontWeight = FontWeight.Bold, fontSize = 14.sp)) {
            append("Log In here!")
        }
        pop()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBeigeBackground),
        contentAlignment = Alignment.Center
    ) {
        // Form utama sekarang bisa di-scroll
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // Membuat seluruh layar bisa di-scroll
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
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
                        text = "Create Account",
                        fontSize = 24.sp,
                        fontFamily = PoppinsFamily,
                        fontWeight = FontWeight.Bold,
                        color = TextColorInsideCard,
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Menggunakan AppTextField yang sudah ditingkatkan
                    AppTextField(value = registerViewModel.fullName, onValueChange = { registerViewModel.updateFullName(it) }, labelText = "Full Name", leadingIcon = Icons.Default.Person)
                    Spacer(modifier = Modifier.height(16.dp))
                    AppTextField(value = registerViewModel.username, onValueChange = { registerViewModel.updateUsername(it) }, labelText = "Username", leadingIcon = Icons.Default.Person)
                    Spacer(modifier = Modifier.height(16.dp))
                    AppTextField(value = registerViewModel.email, onValueChange = { registerViewModel.updateEmail(it) }, labelText = "Email", keyboardType = KeyboardType.Email, leadingIcon = Icons.Default.Email)
                    Spacer(modifier = Modifier.height(16.dp))
                    AppTextField(value = registerViewModel.password, onValueChange = { registerViewModel.updatePassword(it) }, labelText = "Password", isPassword = true, leadingIcon = Icons.Default.Lock)
                    Spacer(modifier = Modifier.height(16.dp))
                    AppTextField(value = registerViewModel.confirmPassword, onValueChange = { registerViewModel.updateConfirmPassword(it) }, labelText = "Confirm Password", isPassword = true, leadingIcon = Icons.Default.Lock)

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = {
                            registerViewModel.onRegisterClick(
                                onRegisterSuccess = {
                                    navController.navigate(AlurApp.HOME_SCREEN) {
                                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                                    }
                                }
                            )
                        },
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        modifier = Modifier.padding(horizontal = 32.dp)
                    ) {
                        Text(text = "Sign Up", fontSize = 16.sp, color = ButtonTextColor)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    ClickableText(
                        text = annotatedString,
                        onClick = { offset ->
                            annotatedString.getStringAnnotations(tag = "LOGIN", start = offset, end = offset)
                                .firstOrNull()?.let {
                                    navController.navigate(AlurApp.LOGIN_SCREEN) {
                                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                                    }
                                }
                        }
                    )
                }
            }
        }
    }
}

// Composable baru yang bisa dipakai ulang di banyak layar
// Disarankan untuk memindahkan Composable ini ke file Komponen.kt agar lebih rapi
@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    labelText: String,
    leadingIcon: ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(labelText) },
        leadingIcon = { Icon(leadingIcon, contentDescription = null, tint = TextColorInsideCard.copy(alpha = 0.7f)) },
        textStyle = TextStyle(color = TextColorInsideCard, fontSize = 16.sp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White.copy(alpha = 0.1f),
            unfocusedContainerColor = Color.Transparent,
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White.copy(alpha = 0.7f),
            cursorColor = TextColorInsideCard,
            focusedLabelColor = TextColorInsideCard,
            unfocusedLabelColor = TextColorInsideCard.copy(alpha = 0.7f),
            focusedLeadingIconColor = Color.White,
            unfocusedLeadingIconColor = TextColorInsideCard.copy(alpha = 0.7f)
        )
    )
}


@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(navController = rememberNavController())
}