package com.capstone.momomeal.data.dto

import com.google.gson.annotations.SerializedName

data class RegisterForm(
    @SerializedName("email")
    val email: String,

    @SerializedName("pwd")
    val pwd: String,

    @SerializedName("Rname")
    val Rname: String
)

/*
* {
    "email": "rladygks329@naver.com",
    "pwd": "1234",
    "Rname": "김요한"
}
* */