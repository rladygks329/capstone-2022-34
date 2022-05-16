package com.capstone.momomeal.data.dto

import com.capstone.momomeal.data.Category
import com.google.gson.annotations.SerializedName

data class CreateChatForm(
    @SerializedName("title") val title: String = "",
    @SerializedName("hostId") val hostId: Int,
    @SerializedName("categoryName") val categoryName: Category,
    @SerializedName("storeName") val storeName: String = "",
    @SerializedName("pickupPlaceName") val pickupPlaceName: String = "",
    @SerializedName("pickupPlaceXCoord") val pickupPlaceXCoord: Double = 1.0,
    @SerializedName("pickupPlaceYCoord") val pickupPlaceYCoord: Double = 1.0
)
{

}
/*
* {
  "title":"피자 부수실 분",
  "hostId":3,
  "categoryName":"Pizza",
  "maxCapacity":3,
  "storeName":"도미노피자",
  "pickupPlaceName":"길음역 4번 출구",
  "pickupPlaceXCoord":4,
  "pickupPlaceYCoord":5
}
* */