package com.example.unihome.models

import com.google.gson.annotations.SerializedName

data class Room(
    @SerializedName("ID") val ID: Int?,
    @SerializedName("doorNumber") val doorNumber: String?,
    @SerializedName("floor") val floor: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("roomPhoto") val roomPhoto: String?,
    @SerializedName("RoomTypeID") val RoomTypeID: Int?,
    @SerializedName("RoomType") val roomType: RoomType?,
    @SerializedName("ResidenceID") val ResidenceID: Int?,
    @SerializedName("Residence") val residence: Residence?,
)
