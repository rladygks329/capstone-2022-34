package com.capstone.momomeal.api

import com.capstone.momomeal.data.Rate
import com.capstone.momomeal.data.Review
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class DeserializerReview: JsonDeserializer<Review> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Review {
        val jsonObject = json?.asJsonObject ?: throw NullPointerException("Response Json String is null")
        val RateStatus = jsonObject["RateStatus"].asString
        val review_text = jsonObject["review_text"].asString
        val rate = Rate.valueOf(RateStatus)

        return Review(rate, review_text)
    }
}