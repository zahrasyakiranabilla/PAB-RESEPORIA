package com.example.ppab_reseporia

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

sealed class LoginEvent {
    data class LoginSuccess(val username: String, val email: String) : LoginEvent()
    object ShowShakeAnimation : LoginEvent()
}
class LoginViewModel : ViewModel() {
    // input fields
    var username by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    // animation trigger
    var shakeButtonTrigger by mutableStateOf(0)
        private set

    fun updateUsername(newUsername: String) {
        username = newUsername
    }

    fun updateEmail(newEmail: String) {
        email = newEmail
    }

    fun updatePassword(newPassword: String) {
        password = newPassword
    }

    // login logic
    fun login(onLoginSuccess: () -> Unit) {
        if (username.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
            viewModelScope.launch {
                delay(500)
                onLoginSuccess()
            }
        } else {
            shakeButtonTrigger++
        }
    }

    fun resetShakeTrigger() {
        shakeButtonTrigger = 0
    }
}