package com.example.unihome.models

import com.google.gson.annotations.SerializedName

data class RoomType(
    @SerializedName("ID") val ID: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("price") val price: Float?,
    @SerializedName("description") val description: String?,
)
