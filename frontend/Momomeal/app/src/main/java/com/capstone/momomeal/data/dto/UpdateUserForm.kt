package com.capstone.momomeal.data.dto

import com.google.gson.annotations.SerializedName

data class UpdateUserForm(
    @SerializedName("user_id") val user_id: Long = 0,
    @SerializedName("email") val email: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("profileImgUrl") val profileImgUrl: String = ""
)
{
}