package com.example.unihome.models

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class Message(
    @SerializedName("ID") val ID: Int?,
    @SerializedName("text") val text: String,
    @SerializedName("dateSended") val dateSended: Timestamp?,
    @SerializedName("ChatID") val ChatID: Int?,
    @SerializedName("Chat") val chat: Chat?,
    @SerializedName("UserID") val UserID: Int?,
    @SerializedName("User") val user: User?
)