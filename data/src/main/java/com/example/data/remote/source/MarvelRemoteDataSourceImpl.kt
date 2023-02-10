package com.example.data.remote.source

import com.example.data.BuildConfig
import com.example.data.remote.network.MarvelApiService
import com.example.data.repository.model.MarvelCharacterRepositoryModel
import java.math.BigInteger
import java.security.MessageDigest
import javax.inject.Inject

internal class MarvelRemoteDataSourceImpl @Inject constructor(
    private val marvelApiService: MarvelApiService
) : MarvelRemoteDataSource {

    override suspend fun getMarvelCharacters(
        nameStartsWith: String,
        offset: Int
    ): Result<List<MarvelCharacterRepositoryModel>> {
        return marvelApiService.getMarvelCharacters(
            nameStartsWith = nameStartsWith,
            limit = MAX_LIMIT,
            offset = offset,
            ts = TS,
            apikey = BuildConfig.PUBLIC_KEY,
            hash = md5()
        ).map { marvelResponse ->
            marvelResponse.data.results.map { result ->
                result.toRepositoryModel()
            }
        }
    }

    private fun md5(): String {
        val input = "1" + BuildConfig.PRIVATE_KEY + BuildConfig.PUBLIC_KEY
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

    companion object {
        private const val MAX_LIMIT = 10
        private const val TS = 1
    }

}