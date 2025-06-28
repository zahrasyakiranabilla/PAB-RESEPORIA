package com.example.ppab_reseporia

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {

    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var shakeButtonTrigger by mutableStateOf(0)
        private set

    fun updateEmail(newEmail: String) {
        email = newEmail
    }

    fun updatePassword(newPassword: String) {
        password = newPassword
    }

    fun resetShakeTrigger() {
        shakeButtonTrigger = 0
    }

    // 🔐 LOGIN ke Firebase
    fun login(onLoginSuccess: () -> Unit) {
        if (email.isBlank() || password.isBlank()) {
            Log.w("LoginViewModel", "❌ Email atau Password kosong")
            shakeButtonTrigger++
            return
        }

        val auth = FirebaseAuth.getInstance()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Firebase", "✅ Login berhasil")
                    onLoginSuccess()
                } else {
                    Log.e("Firebase", "❌ Login gagal: ${task.exception?.message}")
                    shakeButtonTrigger++
                }
            }
    }
}
