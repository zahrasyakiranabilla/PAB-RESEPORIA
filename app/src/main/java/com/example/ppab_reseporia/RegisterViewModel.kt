package com.example.ppab_reseporia

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    // State untuk input form
    var fullName by mutableStateOf("")
        private set
    var username by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var confirmPassword by mutableStateOf("")
        private set

    // State BARU untuk menampung pesan error
    var emailError by mutableStateOf<String?>(null)
        private set
    var passwordError by mutableStateOf<String?>(null)
        private set
    var confirmPasswordError by mutableStateOf<String?>(null)
        private set


    fun updateFullName(input: String) { fullName = input }
    fun updateUsername(input: String) { username = input }
    fun updateEmail(input: String) { email = input.also { emailError = null } }
    fun updatePassword(input: String) { password = input.also { passwordError = null } }
    fun updateConfirmPassword(input: String) { confirmPassword = input.also { confirmPasswordError = null } }


    // Fungsi privat untuk melakukan validasi
    private fun validate(): Boolean {
        // Reset error sebelum validasi baru
        emailError = null
        passwordError = null
        confirmPasswordError = null

        var isValid = true

        // 1. Validasi Email
        if (!email.endsWith("@gmail.com", ignoreCase = true)) {
            emailError = "Email harus menggunakan @gmail.com"
            isValid = false
        }

        // 2. Validasi Panjang Password
        if (password.length < 8) {
            passwordError = "Password minimal 8 karakter"
            isValid = false
        }

        // 3. Validasi Spesial Karakter di Password menggunakan Regex
        // Regex ini mencari setidaknya satu karakter yang BUKAN huruf atau angka
        val specialCharPattern = Regex(".*[^A-Za-z0-9].*")
        if (!password.matches(specialCharPattern)) {
            // Jika passwordError sudah ada isinya, tambahkan pesan baru. Jika tidak, buat pesan baru.
            passwordError = (passwordError?.let { "$it & " } ?: "") + "Harus ada spesial karakter"
            isValid = false
        }

        // 4. Validasi Konfirmasi Password
        if (password != confirmPassword) {
            confirmPasswordError = "Password tidak cocok"
            isValid = false
        }

        return isValid
    }

    fun onRegisterClick(onRegisterSuccess: () -> Unit) {
        // Panggil fungsi validate() terlebih dahulu
        if (validate()) {
            // Jika semua validasi lolos, baru jalankan logika registrasi
            viewModelScope.launch {
                println("Registrasi berhasil untuk user: $username")
                onRegisterSuccess()
            }
        } else {
            // Jika validasi gagal, tidak melakukan apa-apa.
            // UI akan otomatis update karena state error sudah diubah.
            println("Validasi gagal!")
        }
    }
}