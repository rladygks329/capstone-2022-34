package com.capstone.momomeal.api

import com.capstone.momomeal.data.dto.Member
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class DeserializerMember: JsonDeserializer<Member> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Member {
        val jsonObject = json?.asJsonObject ?: throw NullPointerException("Response Json String is null")
        val userId = jsonObject["userId"].asInt
        val email = jsonObject["email"].asString
        val name = jsonObject["name"].asString
        val x = jsonObject["x"].asDouble
        val y = jsonObject["y"].asDouble
        var img = ""
        var address = ""

        if(!jsonObject.get("img_url").isJsonNull) {
            img = jsonObject["img_url"].asString
        }
        if(!jsonObject.get("address").isJsonNull) {
            address = jsonObject["address"].asString
        }
        return Member(userId, email, name, img, x, y, address)
    }
}

/*
    val userId: Int = 0,
    val email: String = "",
    val name: String = "",
    val img_url1: String = "",
    val x: Double =  1.0,
    val y: Double =  0.0,
    val address: String = "",
    val user_late: Int = 50
* */