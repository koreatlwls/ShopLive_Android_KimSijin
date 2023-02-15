package com.example.data.repository

import com.example.data.remote.source.MarvelRemoteDataSource
import com.example.domain.model.MarvelCharacter
import com.example.domain.repository.MarvelRepository
import javax.inject.Inject

internal class MarvelRepositoryImpl @Inject constructor(
    private val marvelRemoteDataSource: MarvelRemoteDataSource
) : MarvelRepository {

    override suspend fun getMarvelCharacters(
        nameStartsWith: String,
        offset: Int
    ): Result<List<MarvelCharacter>> {
        return marvelRemoteDataSource.getMarvelCharacters(
            nameStartsWith,
            offset,
        ).map { repositoryModels ->
            repositoryModels.map { repositoryModel ->
                repositoryModel.toUseCaseModel()
            }
        }
    }

}