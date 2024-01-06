package com.example.unihome.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.unihome.entities.RoomTypeEntity

@Dao
interface RoomTypeDao {
    @Query("SELECT * FROM roomType")
    fun getAll(): List<RoomTypeEntity>
    @Query("SELECT * FROM roomType WHERE ID = :id")
    fun findById(id: Int): RoomTypeEntity
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg roomType: RoomTypeEntity)
}