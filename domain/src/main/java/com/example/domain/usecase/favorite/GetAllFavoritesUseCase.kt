package com.example.domain.usecase.favorite

import com.example.domain.model.MarvelCharacter
import com.example.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFavoritesUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {

    operator fun invoke(): Flow<List<MarvelCharacter>> {
        return favoriteRepository.getAllFavorites()
    }

}