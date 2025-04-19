package com.appdevelopment.dictionary.features_dictionary.data.remote.dto


import com.appdevelopment.dictionary.features_dictionary.data.local.entity.WordInfoEntity
import com.appdevelopment.dictionary.features_dictionary.domain.model.WordInfo

data class WordInfoDto(
    val license: LicenseDto,
    val meanings: List<MeaningDto>,
    val phonetic: String,
    val phonetics: List<PhoneticDto>,
    val sourceUrls: List<String>,
    val word: String
) {
    fun toWordinfoEntity() : WordInfoEntity {
        return WordInfoEntity (
            meanings = meanings.map { it.toMeaning() },
            phonetic = phonetic,
            word = word
        )
    }
}