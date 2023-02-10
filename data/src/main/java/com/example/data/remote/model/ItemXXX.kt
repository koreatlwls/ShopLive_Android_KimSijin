package com.example.data.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ItemXXX(
    val name: String,
    val resourceURI: String,
    val type: String
)