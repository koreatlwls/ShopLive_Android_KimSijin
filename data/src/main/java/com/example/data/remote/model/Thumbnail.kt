package com.example.data.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class Thumbnail(
    val extension: String,
    val path: String
)