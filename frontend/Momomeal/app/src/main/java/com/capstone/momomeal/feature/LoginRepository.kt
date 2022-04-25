package com.capstone.momomeal.feature

object LoginRepository {
    suspend fun login(email: String, password: String): User? {
        return if(email == "user1234" && password == "1234") fakeUser else null
    }
}