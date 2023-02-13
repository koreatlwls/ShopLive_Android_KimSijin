package com.example.data.local.source

import com.example.data.local.database.dao.FavoriteDao
import com.example.data.repository.model.FavoriteRepositoryModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class FavoriteLocalDataSourceImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
) : FavoriteLocalDataSource {

    override suspend fun insertFavorite(favoriteRepositoryModel: FavoriteRepositoryModel) {
        favoriteDao.insertFavorite(favoriteRepositoryModel.toEntity())
    }

    override suspend fun selectId(characterId: String): Boolean {
        return favoriteDao.selectId(characterId)
    }

    override suspend fun deleteFavoriteWithId(characterId: String) {
        favoriteDao.deleteFavoriteWithId(characterId)
    }

    override fun getAllFavorites(): Flow<List<FavoriteRepositoryModel>> {
        return favoriteDao.getAllFavorites().map { favoriteEntities ->
            favoriteEntities.map { favoriteEntity ->
                favoriteEntity.toRepositoryModel()
            }
        }
    }

}