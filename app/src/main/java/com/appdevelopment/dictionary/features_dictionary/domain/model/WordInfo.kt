package com.appdevelopment.dictionary.features_dictionary.domain.model

import com.appdevelopment.dictionary.features_dictionary.data.remote.dto.LicenseDto
import com.appdevelopment.dictionary.features_dictionary.data.remote.dto.MeaningDto
import com.appdevelopment.dictionary.features_dictionary.data.remote.dto.PhoneticDto

data class WordInfo(
    //val license: LicenseDto,
    val meanings: List<Meaning>,
    val phonetic: String,
    //val phonetics: List<PhoneticDto>,
    //val sourceUrls: List<String>,
    val word: String
)
