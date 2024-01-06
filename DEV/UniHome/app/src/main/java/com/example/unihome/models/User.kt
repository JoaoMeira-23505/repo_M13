package com.example.unihome.models

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("ID") val ID: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("surname") val surname: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("password") val password: String?,
    @SerializedName("confirmPassword") val confirmPassword: String?,
    @SerializedName("gender") val gender: String?,
    @SerializedName("nationality") val nationality: String?,
    @SerializedName("nif") val nif: String?,
    @SerializedName("phoneNumber") val phoneNumber: Int?,
    @SerializedName("status") val status: String?,
    @SerializedName("role") val role: String?,
    @SerializedName("phoneToken") val phoneToken: String?,
    @SerializedName("userPhoto") val userPhoto: String?
)
