package com.example.unihome.services

import com.example.unihome.models.Cleaning
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CleaningService {
    @GET("listCleanings")
    fun getAllCleanings(@Header("Authorization") token: String?): Call<List<Cleaning>>
    @GET("listCleaningsByUserId/{UserID}")
    fun getAllCleaningsByUserId(@Header("Authorization") token: String?, @Path("UserID") userID: Int): Call<List<Cleaning>>
    @GET("listCleaning/{ID}")
    fun getCleaningById(@Header("Authorization") token: String?, @Path("ID") id: Int): Call<Cleaning>

    @POST("createCleaning")
    fun addCleaning(@Header("Authorization") token: String?, @Body body: Cleaning): Call<Cleaning>

    @PUT("updateCleaning/{ID}")
    fun updateCleaning(@Header("Authorization") token: String?, @Path("ID") id: Int, @Body body: Cleaning): Call<Cleaning>
}