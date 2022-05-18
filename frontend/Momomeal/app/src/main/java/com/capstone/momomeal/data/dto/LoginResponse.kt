package com.capstone.momomeal.data.dto

import com.google.gson.annotations.SerializedName


data class LoginResponse(
    @SerializedName("member")
    val member: Member?,
    @SerializedName("recommend")
    val recommend:String = ""
){
}