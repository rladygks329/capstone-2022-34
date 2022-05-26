package com.capstone.momomeal.api

import android.util.Log
import com.capstone.momomeal.data.Category
import com.capstone.momomeal.data.Chatroom
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DeserializeChatroom : JsonDeserializer<Chatroom> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Chatroom {
        val jsonObject = json?.asJsonObject ?: throw NullPointerException("Response Json String is null")

        val id = jsonObject["id"].asLong
        val title = jsonObject["title"].asString
        val pickupPlaceName = jsonObject["pickupPlaceName"].asString
        val maxCapacity = jsonObject["maxCapacity"].asInt
        val storeName = jsonObject["storeName"].asString

        var pattern = "yyyy-MM-dd'T'HH:mm:ss."
        var dateString = jsonObject["createdDate"].asString.length + 2 //'T'
        while(pattern.length < dateString){
            pattern += "S"
        }

        val createdDate : LocalDateTime = LocalDateTime.parse(
            jsonObject["createdDate"].asString,
            DateTimeFormatter.ofPattern(pattern)
        ) // Change to LocalDateTime(str to ldt)*/

        val chatroomCategory : Category
        val category = jsonObject["category"].asString
        when(category){
            "CHICKEN"-> chatroomCategory = Category.Chicken
            "PIZZA"-> chatroomCategory = Category.Pizza
            "KOREAN"-> chatroomCategory = Category.Korean
            "CHINESE"-> chatroomCategory = Category.Chinese
            "JAPANESE"-> chatroomCategory = Category.Japanese
            "WESTERN"-> chatroomCategory = Category.Western
            "SNACKBAR"-> chatroomCategory = Category.Snackbar
            "MIDNIGHTSNACK"-> chatroomCategory = Category.MidnightSnack
            "BOILEDPORK"-> chatroomCategory = Category.BoiledPork
            "CAFE"-> chatroomCategory = Category.CafeAndDesert
            "FASTFOOD"-> chatroomCategory = Category.Fastfood
            else -> chatroomCategory = Category.Chicken
        }

        val x = jsonObject["pickupPlaceXCoord"].asDouble
        val y = jsonObject["pickupPlaceYCoord"].asDouble
        return Chatroom(
            id, chatroomCategory, title, maxCapacity, storeName, pickupPlaceName, createdDate, x, y)
    }
}
/*
{
    id": 33,
    "category": "WESTERN",
    "title": "파스타 먹을 사람",
    "hostId": 3,
    "maxCapacity": 4,
    "storeName": "빠네 파스타 전문점",
    "pickupPlaceName": "스파오티",
    "createdDate": "2022-05-12T18:43:28.660192"
}
*/