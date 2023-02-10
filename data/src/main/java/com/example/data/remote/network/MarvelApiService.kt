package com.example.data.remote.network

import com.example.data.remote.model.MarvelResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface MarvelApiService {

    @GET("/v1/public/characters")
    suspend fun getMarvelCharacters(
        @Query("nameStartsWith") nameStartsWith: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("ts") ts: Int,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String
    ): Result<MarvelResponse>

}