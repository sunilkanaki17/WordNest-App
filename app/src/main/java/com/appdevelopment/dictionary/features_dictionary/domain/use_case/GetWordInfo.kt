package com.appdevelopment.dictionary.features_dictionary.domain.use_case

import com.appdevelopment.dictionary.features_dictionary.domain.model.WordInfo
import com.appdevelopment.dictionary.features_dictionary.domain.repository.WordInfoRepository
import com.plcoding.dictionary.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetWordInfo (
    private val repository : WordInfoRepository
    ) {
    operator fun invoke(word: String) : Flow<Resource<List<WordInfo>>> {
        if(word.isBlank()) {
            return flow {  }
        }
        return repository.getWordInfo(word)
    }
}