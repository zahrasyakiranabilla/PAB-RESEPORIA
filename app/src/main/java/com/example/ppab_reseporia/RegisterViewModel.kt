package com.example.ppab_reseporia

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

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

    // State untuk error
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

    // Validasi input
    private fun validate(): Boolean {
        emailError = null
        passwordError = null
        confirmPasswordError = null

        var isValid = true

        if (!email.endsWith("@gmail.com", ignoreCase = true)) {
            emailError = "Email harus menggunakan @gmail.com"
            isValid = false
        }

        if (password.length < 8) {
            passwordError = "Password minimal 8 karakter"
            isValid = false
        }

        val specialCharPattern = Regex(".*[^A-Za-z0-9].*")
        if (!password.matches(specialCharPattern)) {
            passwordError = (passwordError?.let { "$it & " } ?: "") + "Harus ada spesial karakter"
            isValid = false
        }

        if (password != confirmPassword) {
            confirmPasswordError = "Password tidak cocok"
            isValid = false
        }

        return isValid
    }

    // 🔥 Fungsi ini dipanggil dari tombol Sign Up
    fun onRegisterClick(onRegisterSuccess: () -> Unit) {
        if (!validate()) {
            Log.w("RegisterViewModel", "❌ Validasi gagal")
            return
        }

        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid
                    if (uid != null) {
                        val user = hashMapOf(
                            "fullName" to fullName,
                            "username" to username,
                            "email" to email
                        )

                        db.collection("users").document(uid)
                            .set(user)
                            .addOnSuccessListener {
                                Log.d("Firestore", "✅ Data user berhasil disimpan ke Firestore")
                                onRegisterSuccess() // tersimpa jika registrasinya sukses
                            }
                            .addOnFailureListener { e ->
                                Log.e("Firestore", "❌ Gagal simpan data user: ${e.message}")
                            }
                    }
                } else {
                    Log.e("Firebase", "❌ Gagal registrasi: ${task.exception?.message}")
                }
            }
    }

}
