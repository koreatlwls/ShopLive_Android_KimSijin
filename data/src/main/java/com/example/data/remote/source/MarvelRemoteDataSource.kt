package com.example.data.remote.source

import com.example.data.repository.model.MarvelCharacterRepositoryModel

internal interface MarvelRemoteDataSource {

    suspend fun getMarvelCharacters(
        nameStartsWith: String,
        offset: Int
    ): Result<List<MarvelCharacterRepositoryModel>>

}