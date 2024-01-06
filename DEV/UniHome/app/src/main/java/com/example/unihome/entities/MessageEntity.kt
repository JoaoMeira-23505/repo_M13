package com.example.unihome.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("message")
data class MessageEntity(@PrimaryKey(true) val ID: Int? = 0,
                         @ColumnInfo("text") val text: String?,
                         @ColumnInfo("dateSended") val dateSended: String?,
                         @ColumnInfo("ChatID") val ChatID: Int?,
                         @ColumnInfo("UserID") val UserID: Int?)