package com.example.unihome.utils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.unihome.daos.MessageDao
import com.example.unihome.daos.RoomTypeDao
import com.example.unihome.daos.UserDao
import com.example.unihome.entities.MessageEntity
import com.example.unihome.entities.RoomTypeEntity
import com.example.unihome.entities.UserEntity

@Database(entities = [MessageEntity::class,RoomTypeEntity::class,UserEntity::class], version = 1)
abstract class InternalDatabase: RoomDatabase() {
    abstract fun messageDao(): MessageDao
    abstract fun roomTypeDao(): RoomTypeDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile private var instance: InternalDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                InternalDatabase::class.java,
                "internalDatabase.db"
            ).build()
    }
}