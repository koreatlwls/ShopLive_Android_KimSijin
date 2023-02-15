package com.example.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.model.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoriteEntity: FavoriteEntity)

    @Query("SELECT EXISTS(SELECT * FROM FavoriteEntity WHERE characterId=:characterId)")
    suspend fun selectId(characterId: String): Boolean

    @Query("DELETE FROM FavoriteEntity WHERE characterId=:characterId")
    suspend fun deleteFavoriteWithId(characterId: String)

    @Query("SELECT * FROM FavoriteEntity ORDER BY id DESC")
    fun getAllFavorites(): Flow<List<FavoriteEntity>>

    @Query("DELETE FROM FavoriteEntity WHERE id NOT IN (SELECT id FROM FavoriteEntity ORDER BY id DESC LIMIT 5)")
    suspend fun deleteAllOldestItem()

}