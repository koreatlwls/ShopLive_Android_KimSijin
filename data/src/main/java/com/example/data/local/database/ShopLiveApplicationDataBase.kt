package com.example.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.database.dao.FavoriteDao
import com.example.data.local.model.FavoriteEntity

@Database(
    entities = [FavoriteEntity::class],
    version = 1,
    exportSchema = false
)
internal abstract class ShopLiveApplicationDataBase : RoomDatabase() {

    abstract fun getFavoriteDao(): FavoriteDao

    companion object {
        const val DB_NAME = "ShopLive.db"
    }

}