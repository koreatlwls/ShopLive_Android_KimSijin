package com.example.data.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class Series(
    val available: String,
    val collectionURI: String,
    val items: List<Item>,
    val returned: String
)