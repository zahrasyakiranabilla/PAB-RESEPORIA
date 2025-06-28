package com.example.ppab_reseporia

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class EditProfileViewModel : ViewModel() {
    var fullName by mutableStateOf("")
    var username by mutableStateOf("")
    var bio by mutableStateOf("")
    var loading by mutableStateOf(false)

    private val uid = FirebaseAuth.getInstance().currentUser?.uid
    private val db = FirebaseFirestore.getInstance()

    fun loadUserProfile() {
        if (uid == null) return
        loading = true

        db.collection("users").document(uid)
            .get()
            .addOnSuccessListener { document ->
                fullName = document.getString("fullName") ?: ""
                username = document.getString("username") ?: ""
                bio = document.getString("bio") ?: ""
                loading = false
            }
            .addOnFailureListener { e ->
                Log.e("EditProfile", "❌ Gagal ambil data profil: ${e.message}")
                loading = false
            }
    }

    fun saveChanges(onSuccess: () -> Unit) {
        if (uid == null) return
        loading = true

        val userMap = hashMapOf(
            "fullName" to fullName,
            "username" to username,
            "bio" to bio
        )

        db.collection("users").document(uid)
            .update(userMap as Map<String, Any>)
            .addOnSuccessListener {
                Log.d("EditProfile", "✅ Data berhasil diperbarui")
                loading = false
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e("EditProfile", "❌ Gagal update: ${e.message}")
                loading = false
            }
    }
}
