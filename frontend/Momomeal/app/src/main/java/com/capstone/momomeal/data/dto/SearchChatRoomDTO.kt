package com.capstone.momomeal.data.dto

import com.capstone.momomeal.data.Category
import com.capstone.momomeal.data.Chatroom
import com.google.gson.annotations.SerializedName

data class SearchChatRoomDTO (
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("pickupPlaceName") val pickupPlaceName: String,
    @SerializedName("createdDate") val createdDate: String?,
    @SerializedName("distance")  val distance: Int,
    //@SerializedName("category")  val category: String
    ) {
    fun toChatroom() : Chatroom {
        return Chatroom(
            idChatroom = id,
            nameRoom = title,
            namePickupPlace = pickupPlaceName,
            category = Category.Chicken
            //category = Category.valueOf(category)
        )
    }
}
/*
{
		"id": 2L,
        "title": "test2",
        "pickupPlaceName": "국민대학교 기숙사 후문",
        "createdDate": "2022-03-15T14:59:03",
		"distance" : 22,
		"category": "CafeAndDesert"
}
 */