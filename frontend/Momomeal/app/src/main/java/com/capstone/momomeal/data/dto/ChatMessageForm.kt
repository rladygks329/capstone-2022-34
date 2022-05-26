package com.capstone.momomeal.data.dto

import com.google.gson.annotations.SerializedName

data class ChatMessageForm(
    @SerializedName("roomId") val roomId : String = "",
    @SerializedName("writer") val writer : String = "",
    @SerializedName("message") val msg : String = ""
) {

}