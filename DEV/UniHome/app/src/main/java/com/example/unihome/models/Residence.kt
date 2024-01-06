package com.example.unihome.models

import com.google.gson.annotations.SerializedName

data class Residence(
    @SerializedName("ID") val ID: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("location") val location: String?,
    @SerializedName("phoneNumber") val phoneNumber: Int?
)