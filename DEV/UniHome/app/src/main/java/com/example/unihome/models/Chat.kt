package com.example.unihome.models

import com.google.gson.annotations.SerializedName

data class Chat(
    @SerializedName("ID") val ID: Int,
    @SerializedName("status") val status: String?,
    @SerializedName("ChatTypeID") val ChatTypeID: Int,
    @SerializedName("ChatType") val chatType: ChatType?
)