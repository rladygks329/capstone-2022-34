package com.capstone.momomeal.data.dto

import com.capstone.momomeal.data.Rate
import com.google.gson.annotations.SerializedName

data class CreateReviewForm (
    @SerializedName("user_id")
    val user_id: Int = 0,
    @SerializedName("review")
    val review: String = "",
    @SerializedName("rate")
    val rate: Rate = Rate.LIKE
){
}