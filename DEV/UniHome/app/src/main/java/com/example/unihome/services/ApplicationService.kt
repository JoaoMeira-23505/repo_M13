package com.example.unihome.services

import com.example.unihome.models.Application
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApplicationService {
    @GET("listApplications")
    fun getAllApplications(@Header("Authorization") token: String?): Call<List<Application>>
    @GET("listApplication/{ID}")
    fun getApplicationById(@Header("Authorization") token: String?, @Path("ID") id: Int): Call<Application>

    @POST("createApplication")
    fun addApplication(@Header("Authorization") token: String?, @Body body: Application): Call<Application>
}