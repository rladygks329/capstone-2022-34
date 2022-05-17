package com.capstone.momomeal.data.dto

import com.google.gson.annotations.SerializedName

data class getUserInfoForm (
    @SerializedName("user_id")
    val user_id : Int = 0
) {
}