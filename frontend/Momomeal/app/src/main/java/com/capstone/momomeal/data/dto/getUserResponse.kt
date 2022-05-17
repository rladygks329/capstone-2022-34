package com.capstone.momomeal.data.dto

import com.capstone.momomeal.data.Review
import com.google.gson.annotations.SerializedName

data class getUserResponse(
    @SerializedName("reviewList")
    val reviewList: List<Review> = listOf(),
    @SerializedName("img_url")
    val img_url:String,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("int")
    val rate: Int
)
{

}
/*
* "reviewList":[],
"img_url": null,
"name": "김우빈",
"email": "rladygks329@gmail.com",
"int": 50
* */