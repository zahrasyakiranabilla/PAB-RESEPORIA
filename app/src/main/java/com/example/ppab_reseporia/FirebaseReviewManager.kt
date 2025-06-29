package com.example.ppab_reseporia

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await

data class FirebaseReview(
    val id: String = "",
    val recipeId: String = "",
    val userId: String = "",
    val userName: String = "",
    val userEmail: String = "",
    val rating: Int = 0,
    val comment: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val helpful: Int = 0,
    val reported: Boolean = false
)

data class FirebaseRecipeRating(
    val recipeId: String = "",
    val averageRating: Double = 0.0,
    val totalReviews: Int = 0,
    val ratingsDistribution: Map<String, Int> = mapOf(), // "1" -> count, "2" -> count, etc.
    val lastUpdated: Long = System.currentTimeMillis()
)

object FirebaseReviewManager {
    private val firestore = FirebaseFirestore.getInstance()
    private val reviewsCollection = firestore.collection("reviews")
    private val ratingsCollection = firestore.collection("recipe_ratings")

    private val _reviews = mutableStateListOf<FirebaseReview>()
    val reviews: SnapshotStateList<FirebaseReview> = _reviews

    private val _recipeRatings = mutableMapOf<String, FirebaseRecipeRating>()

    // Add or update review
    suspend fun addReview(review: FirebaseReview): Result<String> {
        return try {
            val reviewId = if (review.id.isEmpty()) {
                reviewsCollection.document().id
            } else {
                review.id
            }

            val reviewData = review.copy(id = reviewId)

            // Save review to Firestore
            reviewsCollection.document(reviewId).set(reviewData).await()

            // Update local state
            _reviews.removeAll { it.recipeId == review.recipeId && it.userId == review.userId }
            _reviews.add(reviewData)

            // Update recipe rating
            updateRecipeRating(review.recipeId)

            Log.d("FirebaseReview", "✅ Review saved successfully: $reviewId")
            Result.success(reviewId)
        } catch (e: Exception) {
            Log.e("FirebaseReview", "❌ Error saving review: ${e.message}")
            Result.failure(e)
        }
    }

    // Get reviews for specific recipe
    suspend fun getReviewsForRecipe(recipeId: String): List<FirebaseReview> {
        return try {
            val querySnapshot = reviewsCollection
                .whereEqualTo("recipeId", recipeId)
                .whereEqualTo("reported", false)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .await()

            val reviews = querySnapshot.documents.mapNotNull { doc ->
                doc.toObject<FirebaseReview>()
            }

            // Update local cache
            _reviews.removeAll { it.recipeId == recipeId }
            _reviews.addAll(reviews)

            Log.d("FirebaseReview", "✅ Loaded ${reviews.size} reviews for recipe: $recipeId")
            reviews
        } catch (e: Exception) {
            Log.e("FirebaseReview", "❌ Error loading reviews: ${e.message}")
            emptyList()
        }
    }

    // Get user's review for specific recipe
    suspend fun getUserReviewForRecipe(recipeId: String, userId: String): FirebaseReview? {
        return try {
            val querySnapshot = reviewsCollection
                .whereEqualTo("recipeId", recipeId)
                .whereEqualTo("userId", userId)
                .limit(1)
                .get()
                .await()

            val review = querySnapshot.documents.firstOrNull()?.toObject<FirebaseReview>()
            Log.d("FirebaseReview", "✅ User review loaded: ${review?.id}")
            review
        } catch (e: Exception) {
            Log.e("FirebaseReview", "❌ Error loading user review: ${e.message}")
            null
        }
    }

    // Update recipe rating statistics
    private suspend fun updateRecipeRating(recipeId: String) {
        try {
            val reviews = getReviewsForRecipe(recipeId)

            if (reviews.isEmpty()) {
                // Delete rating document if no reviews
                ratingsCollection.document(recipeId).delete().await()
                _recipeRatings.remove(recipeId)
                return
            }

            val totalReviews = reviews.size
            val averageRating = reviews.map { it.rating }.average()

            // Calculate rating distribution
            val distribution = mutableMapOf<String, Int>()
            for (i in 1..5) {
                distribution[i.toString()] = reviews.count { it.rating == i }
            }

            val ratingData = FirebaseRecipeRating(
                recipeId = recipeId,
                averageRating = averageRating,
                totalReviews = totalReviews,
                ratingsDistribution = distribution,
                lastUpdated = System.currentTimeMillis()
            )

            // Save to Firestore
            ratingsCollection.document(recipeId).set(ratingData).await()

            // Update local cache
            _recipeRatings[recipeId] = ratingData

            Log.d("FirebaseReview", "✅ Recipe rating updated: $recipeId - ${String.format("%.1f", averageRating)} ($totalReviews reviews)")
        } catch (e: Exception) {
            Log.e("FirebaseReview", "❌ Error updating recipe rating: ${e.message}")
        }
    }

    // Get recipe rating
    suspend fun getRecipeRating(recipeId: String): FirebaseRecipeRating {
        return try {
            // Check local cache first
            _recipeRatings[recipeId]?.let { return it }

            // Load from Firestore
            val document = ratingsCollection.document(recipeId).get().await()
            val rating = document.toObject<FirebaseRecipeRating>() ?: FirebaseRecipeRating(recipeId = recipeId)

            // Update local cache
            _recipeRatings[recipeId] = rating

            Log.d("FirebaseReview", "✅ Recipe rating loaded: $recipeId")
            rating
        } catch (e: Exception) {
            Log.e("FirebaseReview", "❌ Error loading recipe rating: ${e.message}")
            FirebaseRecipeRating(recipeId = recipeId)
        }
    }

    // Delete review (soft delete by marking as reported)
    suspend fun deleteReview(reviewId: String): Result<Unit> {
        return try {
            reviewsCollection.document(reviewId)
                .update("reported", true)
                .await()

            // Update local state
            _reviews.removeAll { it.id == reviewId }

            Log.d("FirebaseReview", "✅ Review deleted: $reviewId")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("FirebaseReview", "❌ Error deleting review: ${e.message}")
            Result.failure(e)
        }
    }

    // Mark review as helpful
    suspend fun markReviewHelpful(reviewId: String): Result<Unit> {
        return try {
            val reviewDoc = reviewsCollection.document(reviewId)

            firestore.runTransaction { transaction ->
                val snapshot = transaction.get(reviewDoc)
                val currentHelpful = snapshot.getLong("helpful") ?: 0
                transaction.update(reviewDoc, "helpful", currentHelpful + 1)
            }.await()

            Log.d("FirebaseReview", "✅ Review marked as helpful: $reviewId")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("FirebaseReview", "❌ Error marking review as helpful: ${e.message}")
            Result.failure(e)
        }
    }

    // Get all reviews by user
    suspend fun getUserReviews(userId: String): List<FirebaseReview> {
        return try {
            val querySnapshot = reviewsCollection
                .whereEqualTo("userId", userId)
                .whereEqualTo("reported", false)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .await()

            val reviews = querySnapshot.documents.mapNotNull { doc ->
                doc.toObject<FirebaseReview>()
            }

            Log.d("FirebaseReview", "✅ Loaded ${reviews.size} reviews for user: $userId")
            reviews
        } catch (e: Exception) {
            Log.e("FirebaseReview", "❌ Error loading user reviews: ${e.message}")
            emptyList()
        }
    }

    // Listen to real-time updates for recipe reviews
    fun listenToRecipeReviews(recipeId: String, onUpdate: (List<FirebaseReview>) -> Unit) {
        reviewsCollection
            .whereEqualTo("recipeId", recipeId)
            .whereEqualTo("reported", false)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    Log.e("FirebaseReview", "❌ Error listening to reviews: ${error.message}")
                    return@addSnapshotListener
                }

                val reviews = querySnapshot?.documents?.mapNotNull { doc ->
                    doc.toObject<FirebaseReview>()
                } ?: emptyList()

                // Update local cache
                _reviews.removeAll { it.recipeId == recipeId }
                _reviews.addAll(reviews)

                onUpdate(reviews)
                Log.d("FirebaseReview", "🔄 Real-time update: ${reviews.size} reviews for $recipeId")
            }
    }

    // Get trending recipes based on ratings
    suspend fun getTrendingRecipes(limit: Int = 10): List<FirebaseRecipeRating> {
        return try {
            val querySnapshot = ratingsCollection
                .whereGreaterThan("totalReviews", 2) // At least 3 reviews
                .orderBy("totalReviews", Query.Direction.DESCENDING)
                .orderBy("averageRating", Query.Direction.DESCENDING)
                .limit(limit.toLong())
                .get()
                .await()

            val ratings = querySnapshot.documents.mapNotNull { doc ->
                doc.toObject<FirebaseRecipeRating>()
            }

            Log.d("FirebaseReview", "✅ Loaded ${ratings.size} trending recipes")
            ratings
        } catch (e: Exception) {
            Log.e("FirebaseReview", "❌ Error loading trending recipes: ${e.message}")
            emptyList()
        }
    }
}