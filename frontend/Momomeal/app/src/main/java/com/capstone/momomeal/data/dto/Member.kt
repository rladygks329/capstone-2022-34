package com.capstone.momomeal.data.dto

import com.capstone.momomeal.data.User
import com.google.gson.annotations.SerializedName

data class Member(
    @SerializedName("userId")
    val userId: Int = 0,

    @SerializedName("email")
    val email: String = "",

    @SerializedName("name")
    val name: String = "",

    //@SerializedName("img_url")
    val img_url1: String = "",

    //@SerializedName("x")
    val x: Double =  1.0,

    //@SerializedName("y")
    val y: Double =  0.0,

    //@SerializedName("address")
    val address: String = "",

    //@SerializedName("user_rate")
    val user_late: Int = 50
){
    fun toUser(): User {
        return User(name, userId, email, img_url1, user_late, listOf(), x, y, address)
    }
}
/*
* userId": 10,
"email": "qwer",
"pwd": "1111",
"name": "123",
"img_url": null
* x
* y
* address
* */
