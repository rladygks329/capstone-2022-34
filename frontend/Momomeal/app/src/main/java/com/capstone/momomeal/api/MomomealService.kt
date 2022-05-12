package com.capstone.momomeal.api

import com.capstone.momomeal.data.Chatroom
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MomomealService {
    private const val MOMOMEAL_Local_URL = "http://10.0.2.2:8080/"
    private const val MOMOMEAL_AWS_URL = "3.39.60.56:8080"

    private val customgson : Gson by lazy {
        GsonBuilder()
            .registerTypeAdapter(Chatroom::class.java, DeserializeChatroom())
            .create()
    }
    private val retrofit : Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(MOMOMEAL_Local_URL)
            .addConverterFactory(GsonConverterFactory.create(customgson))
            .build()
    }
    val momomealAPI : MomomealAPI by lazy {
        retrofit.create(MomomealAPI::class.java)
    }
}

