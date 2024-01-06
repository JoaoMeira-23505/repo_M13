package com.example.unihome.models

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class Payment(
    @SerializedName("ID") val ID: Int?,
    @SerializedName("description") val description: String?,
    @SerializedName("value") val value: Double?,
    @SerializedName("issueDate") val issueDate: Timestamp?,
    @SerializedName("paymentDate") val paymentDate: Timestamp?,
    @SerializedName("status") val status: String?,
    @SerializedName("PaymentTypeID") val PaymentTypeID: Int?,
    @SerializedName("PaymentType") val paymentType: PaymentType?,
    @SerializedName("UserID") val UserID: Int?,
    @SerializedName("User") val user: User?,
)
