package com.example.unihome.services

import com.example.unihome.models.Room
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface RoomService {
    @GET("listRooms")
    fun getAllRooms(@Header("Authorization") token: String?): Call<List<Room>>
    @GET("listRoom/{ID}")
    fun getRoomByUserId(@Header("Authorization") token: String?, @Path("ID") id: Int): Call<List<Room>>
}