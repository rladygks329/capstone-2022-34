package com.capstone.momomeal.feature

import com.capstone.momomeal.api.MomomealAPI
import com.capstone.momomeal.api.MomomealService
import com.capstone.momomeal.data.User
import com.capstone.momomeal.data.fakeUser

class LoginRepository(
    private val momomeal: MomomealAPI = MomomealService.momomealAPI
) {
    suspend fun login(email: String, password: String): User? {
        return if(email == "user1234" && password == "1234") fakeUser else null
    }
}