package com.example.domain.usecase.favorite

import com.example.domain.model.MarvelCharacter
import com.example.domain.repository.FavoriteRepository
import javax.inject.Inject

class InsertFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {

    suspend operator fun invoke(marvelCharacter: MarvelCharacter) {
        favoriteRepository.insertFavorite(marvelCharacter)
    }

}