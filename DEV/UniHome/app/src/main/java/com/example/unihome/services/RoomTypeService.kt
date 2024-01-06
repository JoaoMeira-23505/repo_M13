package com.example.unihome.services

import com.example.unihome.models.RoomType
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RoomTypeService {
    @GET("listRoomTypes")
    fun getAllRoomTypes(): Call<List<RoomType>>
    @GET("listRoomType/{ID}")
    fun getRoomTypeById(@Path("ID") id: Int): Call<RoomType>

}