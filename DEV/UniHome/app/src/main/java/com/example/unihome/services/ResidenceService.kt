package com.example.unihome.services

import com.example.unihome.models.Residence
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ResidenceService {
    @GET("listResidences")
    fun getAllResidences(@Header("Authorization") token: String?): Call<List<Residence>>
    @GET("listResidence/{ID}")
    fun getResidenceById(@Header("Authorization") token: String?, @Path("ID") id: Int): Call<Residence>
}