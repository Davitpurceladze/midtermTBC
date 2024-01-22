package com.example.job_search.data.repository.auth

import com.example.job_search.data.common.HandleAuthResponse
import com.example.job_search.domein.repository.auth.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val handleAuthResponse: HandleAuthResponse
) : AuthRepository {
    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun login(email: String, password: String) =
        handleAuthResponse.safeAuthCall {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
        }

    override suspend fun registration(email: String, password: String) =
        handleAuthResponse.safeAuthCall {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        }

    //    override suspend fun login(email: String, password: String): Resource<FirebaseUser> {
//        return try {
//            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
//            Resource.Success(result.user!!)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            Resource.Failure(e)
//        }
//    }

//    override suspend fun registration(email: String, password: String): Resource<FirebaseUser> {
//        return try {
//            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
//            Resource.Success(result.user!!)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            Resource.Failure(e)
//        }
//    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}