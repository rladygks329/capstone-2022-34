package com.capstone.momomeal.api

import com.capstone.momomeal.feature.Chatroom
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MomomealService {
    private const val MOMOMEAL_URL = "http://172.30.1.17:8000/"
    private val gson : Gson by lazy {
        GsonBuilder()
            .registerTypeAdapter(Chatroom::class.java, DeserializeChatroom())
            .create()
    }
    private val retrofit : Retrofit by lazy {
        Retrofit
            .Builder()
            .baseUrl(MOMOMEAL_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
    val momomealAPI : MomomealAPI by lazy {
        retrofit.create(MomomealAPI::class.java)
    }
}

