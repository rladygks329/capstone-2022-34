package com.capstone.momomeal.data.dto

import com.google.gson.annotations.SerializedName

data class UpdateUserInfoForm(
    @SerializedName("user_id")
    val user_id: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("profileImgUrl")
    val profileImgUrl: String
){
}
/*
  "user_id": 3,
  "email": "xxx@gmail.com",
  "name": "김요한",
  "profileImgUrl": "xxx"
*
* */