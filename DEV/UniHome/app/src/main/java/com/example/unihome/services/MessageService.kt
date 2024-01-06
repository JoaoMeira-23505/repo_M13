package com.example.unihome.services

import com.example.unihome.models.Message
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface MessageService {
    @GET("listMessages")
    fun getAllMessages(@Header("Authorization") token: String?): Call<List<Message>>
    @GET("listMessagesByChatId/{ID}")
    fun getMessagesByChatId(@Header("Authorization") token: String?, @Path("ID") id: Int): Call<List<Message>>

    @POST("createMessage")
    fun addMessage(@Header("Authorization") token: String?, @Body body: Message): Call<Message>
}