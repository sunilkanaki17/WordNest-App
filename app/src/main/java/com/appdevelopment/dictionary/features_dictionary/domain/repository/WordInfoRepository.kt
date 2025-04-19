package com.appdevelopment.dictionary.features_dictionary.domain.repository

import com.appdevelopment.dictionary.features_dictionary.domain.model.WordInfo
import com.plcoding.dictionary.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface WordInfoRepository {
    fun getWordInfo(word : String) : Flow<Resource<List<WordInfo>>>
}