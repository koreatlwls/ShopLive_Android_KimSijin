package com.example.domain.repository

import com.example.domain.model.MarvelCharacter

interface FavoriteRepository {

    suspend fun insertFavorite(marvelCharacter: MarvelCharacter)

    suspend fun selectId(characterId: String): Boolean

    suspend fun deleteFavoriteWithId(characterId: String)

}