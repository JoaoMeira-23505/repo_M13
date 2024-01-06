package com.example.unihome.models

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class Application(
    @SerializedName("ID") val ID: Int?,
    @SerializedName("dateSubmitted") val dateSubmitted: Timestamp?,
    @SerializedName("status") val status: String?,
    @SerializedName("sosNumber") val sosNumber: Int?,
    @SerializedName("courseName") val courseName: String?,
    @SerializedName("courseYearAttended") val courseYearAttended: String?,
    @SerializedName("courseYearStarted") val courseYearStarted: String?,
    @SerializedName("lastYearStatus") val lastYearStatus: Boolean?,
    @SerializedName("socialBenefits") val socialBenefits: Boolean?,
    @SerializedName("observations") val observations: String?,
    @SerializedName("UserID") val UserID: Int?,
    @SerializedName("User") val user: User?,
    @SerializedName("RoomTypeID") val RoomTypeID: Int?,
    @SerializedName("RoomType") val roomType: RoomType?,
    @SerializedName("ResidenceID") val ResidenceID: Int?,
    @SerializedName("Residence") val residence: Residence?,
)