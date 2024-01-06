package com.example.unihome.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("roomType")
data class RoomTypeEntity (@PrimaryKey(true) val ID: Int? = 0,
                           @ColumnInfo("name") val name: String?,
                           @ColumnInfo("price") val price: Float?,
                           @ColumnInfo("description") val description: String?)