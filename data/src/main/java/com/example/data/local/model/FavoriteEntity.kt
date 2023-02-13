package com.example.data.local.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.data.repository.model.FavoriteRepositoryModel

@Entity(indices = [Index(value = ["characterId"], unique = true)])
internal data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val characterId: String,
    val name: String,
    val description: String,
    val thumbnail: String
) {

    fun toRepositoryModel() = FavoriteRepositoryModel(
        characterId = characterId,
        name = name,
        description = description,
        thumbnail = thumbnail
    )

}