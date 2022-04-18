package com.capstone.momomeal.repository

import com.capstone.momomeal.feature.User

interface LoginRepository {
    suspend fun logIn(email:String, password:String): User?
}

//singleton
object LoginRepositoryImpl : LoginRepository {
   override suspend fun logIn(email:String, password:String): User? {
       return if(email == "user123" && password == "1234"){
           User()
       } else null
    }
}