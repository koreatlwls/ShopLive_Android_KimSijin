package com.example.domain.usecase.search

import com.example.domain.model.MarvelCharacter
import com.example.domain.repository.MarvelRepository
import javax.inject.Inject

class GetMarvelCharacterUseCase @Inject constructor(
    private val marvelRepository: MarvelRepository
) {

    suspend operator fun invoke(
        nameStartsWith : String,
        offset : Int,
    ) : Result<List<MarvelCharacter>> {
        return marvelRepository.getMarvelCharacters(
            nameStartsWith,
            offset
        )
    }

}