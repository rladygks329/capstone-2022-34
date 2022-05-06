package com.capstone.momomeal.api

import com.capstone.momomeal.data.dto.*
import com.capstone.momomeal.data.dto.LoginResponse
import com.capstone.momomeal.feature.Chatroom
import retrofit2.Call
import retrofit2.http.*

interface MomomealAPI {
    @POST("/chat")
    fun makeChatroom(

    )

    @GET("/chat-list/{categoryName}/{memberId}/{type}")
    fun getCategoryChatroom(
        @Path("categoryName") categoryName: String,
        @Path("memberId") memberId: Int,
        @Path("type") type:String
    ): Call<List<SearchChatRoomDTO>>

    @GET("/chat-list/{memberId}/{type}")
    fun getAllChatroom(
       @Path("memberId") memberId:Int,
       @Path("type") type:String
   ): Call<List<Chatroom>>

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
   ): Call<List<MyChatRoomDTO>>

    @DELETE("/deleted-chat/{memberId}/{chatroomId}")
    fun deleteChatroom(
       @Path("memberId") memberId: Int,
       @Path("chatroomId") chatroomId: Int
    ): Call<HashMap<String, Int>>

    @GET("/searched-chat-list/{keyword}")
    fun getSearchChatroom(
       @Path("keyword") keyword:String
   ): Call<List<SearchChatRoomDTO>>

    @GET("/entered-chat-info/{chatroomId}")
    fun getEnteredChatInfo(
       @Path("chatroomId") chatroomId: Long
    ): Call<Chatroom>

    @POST("/login.do")
    fun login(
        @Body params: HashMap<String, String>
    ): Call<LoginResponse>

    @POST("/createAccount.do")
    fun register(
        @Body params: RegisterForm
    ): Call<LoginResponse>

}
