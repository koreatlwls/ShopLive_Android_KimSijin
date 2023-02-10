package com.example.domain.repository

import com.example.domain.model.MarvelCharacter

interface MarvelRepository {

    suspend fun getMarvelCharacters(
        nameStartsWith: String,
        offset: Int
    ): Result<List<MarvelCharacter>>

}