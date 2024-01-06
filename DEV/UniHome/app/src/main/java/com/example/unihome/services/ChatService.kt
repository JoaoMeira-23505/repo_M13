package com.example.unihome.services

import com.example.unihome.models.Chat
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ChatService {
    @GET("listChats")
    fun getAllChats(@Header("Authorization") token: String?): Call<List<Chat>>
    @GET("listChat/{ID}")
    fun getChatById(@Header("Authorization") token: String?, @Path("ID") id: Int): Call<Chat>
    @GET("listChatsByUserID/{UserID}")
    fun getChatsByUserId(@Header("Authorization") token: String?, @Path("UserID") id: Int): Call<List<Chat>>
}