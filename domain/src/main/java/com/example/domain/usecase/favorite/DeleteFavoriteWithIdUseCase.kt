package com.example.domain.usecase.favorite

import com.example.domain.repository.FavoriteRepository
import javax.inject.Inject

class DeleteFavoriteWithIdUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {

    suspend operator fun invoke(characterId: String) {
        favoriteRepository.deleteFavoriteWithId(characterId)
    }

}