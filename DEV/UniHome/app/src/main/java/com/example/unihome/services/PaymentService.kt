package com.example.unihome.services

import com.example.unihome.models.Payment
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface PaymentService {
    @GET("listPayments")
    fun getAllPayments(@Header("Authorization") token: String?): Call<List<Payment>>
    @GET("listPaymentsByUserID/{UserID}")
    fun getPaymentsByUserId(@Header("Authorization") token: String?, @Path("UserID") id: Int): Call<List<Payment>>
    @GET("listPayment/{ID}")
    fun getPaymentById(@Header("Authorization") token: String?, @Path("ID") id: Int): Call<Payment>

    @POST("createPayment")
    fun addPayment(@Header("Authorization") token: String?, @Body body: Payment): Call<Payment>
}