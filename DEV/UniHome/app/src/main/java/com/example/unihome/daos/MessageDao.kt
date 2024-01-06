package com.example.unihome.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.unihome.entities.MessageEntity

@Dao
interface MessageDao {
    @Query("SELECT * FROM message")
    fun getAllMessages(): List<MessageEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg message: MessageEntity)
}