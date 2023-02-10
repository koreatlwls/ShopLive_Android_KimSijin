package com.example.data.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class Item(
    val name: String,
    val resourceURI: String
)