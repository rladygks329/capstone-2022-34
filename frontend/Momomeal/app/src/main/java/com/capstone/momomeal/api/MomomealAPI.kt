package com.capstone.momomeal.api

import com.capstone.momomeal.feature.Chatroom
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MomomealAPI {

   @POST("/chat")
    fun makeChatroom(

   )

   @GET("/chat-list/{categoryName}/{memberId}/{type}")
   fun getCategoryChatroom(
        @Path("categoryName") categoryName: String,
        @Path("memberId") memberId: Int,
        @Path("type") type:String
    ): Call<Chatroom>

   @GET("/chat-list/{memberId}/{type}")
   fun getAllChatroom(
       @Path("memberId") memberId:Int,
       @Path("type") type:String
   ): Call<Chatroom>

    @GET("/clicked-chat/{chatroomId}")
    fun getChatroomInfo(
        @Path("chatroomId") chatroomId : Long
    ): Call<Chatroom>

    @GET("/chat/{memberId}/{chatroomId}")
    fun enterChatroom(

    )

   @GET("/entered-chat-list/{memberId}")
   fun getEnteredChatroom(
       @Path("memberId") memberId:Int
   ): Call<Chatroom>

   @DELETE("/deleted-chat/{memberId}/{chatroomId}")
   fun deleteChatroom(
       @Path("memberId") memberId:Int,
       @Path("chatroomId") chatroomId: Long
   )

   @GET("/searched-chat-list/{keyword}")
   fun getSearchChatroom(
       @Path("keyword") keyword:String
   ): Call<Chatroom>

   @GET("/entered-chat-info/{chatroomId}")
   fun getEnteredChatInfo(
       @Path("chatroomId") chatroomId: Long
   ) : Call<Chatroom>

}