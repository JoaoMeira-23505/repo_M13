package com.example.unihome.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("user")
data class UserEntity(@PrimaryKey(true) val id: Int? = 0,
                      @ColumnInfo("name") val name: String?,
                      @ColumnInfo("surname") val surname: String?,
                      @ColumnInfo("email") val email: String?,
                      @ColumnInfo("nif") val nif: String?,
                      @ColumnInfo("phoneNumber") val phoneNumber: Int?,
                      @ColumnInfo("status") val status: String?,
                      @ColumnInfo("role") val role: String?,
                      @ColumnInfo("phoneToken") val phoneToken: String?,
                      @ColumnInfo("userPhoto") val userPhoto: String?,
                      @ColumnInfo("jwtToken") val jwtToken: String?)

