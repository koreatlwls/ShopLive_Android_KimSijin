package com.example.data.local.source

import com.example.data.repository.model.FavoriteRepositoryModel

internal interface FavoriteLocalDataSource {

    suspend fun insertFavorite(favoriteRepositoryModel: FavoriteRepositoryModel)

    suspend fun selectId(characterId: String): Boolean

}