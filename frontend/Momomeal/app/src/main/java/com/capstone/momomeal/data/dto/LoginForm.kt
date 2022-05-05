package com.capstone.momomeal.data.dto

import com.google.gson.annotations.SerializedName

data class LoginForm(
    @SerializedName("email") val email: String,
    @SerializedName("pwd") val password: String
)