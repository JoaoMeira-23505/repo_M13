package com.example.unihome.models

import com.google.gson.annotations.SerializedName

data class PaymentType(
    @SerializedName("ID") val ID: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("description") val description: String?
)
