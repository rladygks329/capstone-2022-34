package com.capstone.momomeal.data.dto

import com.google.gson.annotations.SerializedName

data class UpdateCoordinateForm (
    @SerializedName("user_id")
    val user_id : Int,
    @SerializedName("x")
    val x : Double = 0.0,
    @SerializedName("y")
    val y : Double = 0.0,
    @SerializedName("address")
    val address: String = ""
)


/*
* {
  "user_id":1,
  "x":2,
  "y":3
}
* */