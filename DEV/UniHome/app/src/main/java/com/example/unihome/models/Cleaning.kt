package com.example.unihome.models

import com.google.gson.annotations.SerializedName

data class Cleaning(
    @SerializedName("ID") val ID: Int?,
    @SerializedName("date") val date: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("RoomID") val RoomID: Int?,
    @SerializedName("Room") val room: Room?,
    @SerializedName("UserID") val UserID: Int?,
    @SerializedName("User") val user: User?
)
