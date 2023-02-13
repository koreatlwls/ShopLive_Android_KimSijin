package com.example.data.local.source

import com.example.data.repository.model.FavoriteRepositoryModel
import kotlinx.coroutines.flow.Flow

internal interface FavoriteLocalDataSource {

    suspend fun insertFavorite(favoriteRepositoryModel: FavoriteRepositoryModel)

    suspend fun selectId(characterId: String): Boolean

    suspend fun deleteFavoriteWithId(characterId: String)

    fun getAllFavorites(): Flow<List<FavoriteRepositoryModel>>

}