package com.example.unihome.services

import com.example.unihome.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("authLogin")
    fun authUser(@Body userData: User): Call<Map<String, String>>
    @POST("authRegister")
    fun registerUser(@Body newUserData: User): Call<Map<String, String>>
}