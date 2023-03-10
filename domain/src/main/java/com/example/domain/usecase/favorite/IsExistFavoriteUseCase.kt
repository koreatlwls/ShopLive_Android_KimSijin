package com.example.domain.usecase.favorite

import com.example.domain.repository.FavoriteRepository
import javax.inject.Inject

class IsExistFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {

    suspend operator fun invoke(characterId: String): Boolean {
        return favoriteRepository.selectId(characterId)
    }

}