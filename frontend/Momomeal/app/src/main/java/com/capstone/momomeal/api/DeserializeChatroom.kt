package com.capstone.momomeal.api

import com.capstone.momomeal.feature.Chatroom
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class DeserializeChatroom : JsonDeserializer<Chatroom> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Chatroom {
        val jsonObject = json?.asJsonObject ?: throw NullPointerException("Response Json String is null")

        val id = jsonObject["id"].asInt
        val title = jsonObject["title"].asString
        val pickupPlaceName = jsonObject["pickupPlaceName"].asString
        val createdDate = jsonObject["createdDate"].asString
        val pickupPlaceXCoord = jsonObject["pickupPlaceXCoord"].asDouble
        val pickupPlaceYCoord = jsonObject["pickupPlaceYCoord"].asDouble
        return Chatroom(
            nameRoom = title,
            idChatroom = id,
            namePickupPlace = pickupPlaceName,
            coordPickupPlaceX = pickupPlaceXCoord,
            coordPickupPlaceY = pickupPlaceYCoord
        )
    }
}
/*
* [
    {
		"id": 1L,
        "title": "test1",
        "pickupPlaceName": "국민대학교",
        "createdDate": "2022-03-15T01:33:41",
		"pickupPlaceXCoord": 37.6,
		"pickupPlaceYCoord": 126.9
    },
    {
		"id": 2L,
        "title": "test2",
        "pickupPlaceName": "국민대학교 기숙사 후문",
        "createdDate": "2022-03-15T14:59:03",
		"pickupPlaceXCoord": 37.6,
		"pickupPlaceYCoord": 126.9
    }
* ]
* */