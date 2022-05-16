package com.capstone.momomeal.api

import com.capstone.momomeal.data.Chatroom
import com.capstone.momomeal.data.LoginResponse
import com.capstone.momomeal.data.User_light
import com.capstone.momomeal.data.dto.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface MomomealAPI {
    @POST("/chat")
    fun makeChatroom(
        @Body params: CreateChatForm
    ): Call<Chatroom>

    @GET("/chat-list/{categoryName}/{memberId}/{type}")
    fun getCategoryChatroom(
        @Path("categoryName") categoryName: String,
        @Path("memberId") memberId: Int,
        @Path("type") type:String
    ): Call<List<Chatroom>>

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
        @Path("memberId") memberId: Int,
        @Path("chatroomId") chatroomId: Long
    ): Call<HashMap<String, Int>>

    @GET("/entered-chat-list/{memberId}")
    fun getEnteredChatroom(
       @Path("memberId") memberId:Int
   ): Call<List<Chatroom>>

    @DELETE("/deleted-chat/{memberId}/{chatroomId}")
    fun deleteChatroom(
       @Path("memberId") memberId: Int,
       @Path("chatroomId") chatroomId: Long
    ): Call<HashMap<String, Int>>

    @GET("/searched-chat-list/{keyword}/{memberId}")
    fun getSearchChatroom(
       @Path("keyword") keyword:String,
       @Path("memberId") memberId: Int
   ): Call<List<Chatroom>>

    // getParcelable에서는 List를 넘겨줄 수 없어서 ArrayList로 받았음.
    // 여기만 ArrayList를 사용함. 주의!
    @GET("/entered-chat-info/{chatroomId}")
    fun getEnteredChatInfo(
       @Path("chatroomId") chatroomId: Long
    ): Call<ArrayList<User_light>>

    @GET("/recommend-chat-list/{memberId}")
    fun getRecommendedChatroom(
        @Path("memberId") memberId: Int
    ): Call<List<Chatroom>>

    @POST("/login.do")
    fun login(
        @Body params: HashMap<String, String>
    ): Call<LoginResponse>

    @POST("/createAccount.do")
    fun register(
        @Body params: RegisterForm
    ): Call<LoginResponse>

    @GET("/preferred-category/{memberId}/{categories}")
    fun sendResearch(
        @Path("memberId") memberId: Int,
        @Path("categories") categories: String
    ): Call<ResponseBody>
}
