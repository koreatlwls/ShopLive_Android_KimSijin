package com.example.data.remote.model

import com.example.data.repository.model.MarvelCharacterRepositoryModel
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class Result(
    val comics: Comics,
    val description: String,
    val events: Events,
    val id: String,
    val modified: String,
    val name: String,
    val resourceURI: String,
    val series: Series,
    val stories: Stories,
    val thumbnail: Thumbnail,
    val urls: List<Url>
) {
    fun toRepositoryModel() = MarvelCharacterRepositoryModel(
        id = id,
        name = name,
        description = description,
        thumbnail = "${thumbnail.path}.${thumbnail.extension}"
    )
}