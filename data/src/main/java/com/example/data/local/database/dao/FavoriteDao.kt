package com.example.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.model.FavoriteEntity

@Dao
internal interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoriteEntity: FavoriteEntity)

    @Query("SELECT EXISTS(SELECT * FROM FavoriteEntity WHERE characterId=:characterId)")
    suspend fun selectId(characterId: String): Boolean

}