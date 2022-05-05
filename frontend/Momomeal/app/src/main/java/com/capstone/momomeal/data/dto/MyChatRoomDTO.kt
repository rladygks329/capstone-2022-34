package com.capstone.momomeal.data.dto

import com.capstone.momomeal.feature.Chatroom
import com.google.gson.annotations.SerializedName

data class MyChatRoomDTO(
    @SerializedName("chatRoomId") val id : Int,
    @SerializedName("title") val title : String
){
    fun toChatroom() : Chatroom {
        return Chatroom(nameRoom = title, idChatroom = id)
    }
}