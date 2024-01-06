package com.example.unihome.daos

import androidx.room.Dao
import androidx.room.Query
import com.example.unihome.entities.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE ID = :id")
    fun findById(id: Int): UserEntity
    @Query("SELECT * FROM user WHERE email = :email")
    fun findByEmail(email: String): UserEntity
}