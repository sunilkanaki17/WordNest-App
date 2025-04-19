package com.appdevelopment.dictionary.features_dictionary.data.remote

import com.appdevelopment.dictionary.features_dictionary.data.remote.dto.WordInfoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {
    @GET("/api/v2/entries/en/{word}")      //here word is dynamically sent, the word which user need to search
    suspend fun getWordInfo (
        @Path("word") word : String
    ) : List<WordInfoDto>

    companion object {
        const val BASE_URL = "https://api.dictionaryapi.dev/"
    }
}