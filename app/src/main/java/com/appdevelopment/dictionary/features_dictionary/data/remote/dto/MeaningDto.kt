package com.appdevelopment.dictionary.features_dictionary.data.remote.dto

import com.appdevelopment.dictionary.features_dictionary.domain.model.Meaning

data class MeaningDto(
    //val antonyms: List<Any>,
    val definitions: List<DefinitionDto>,
    val partOfSpeech: String,
    //val synonyms: List<String>
) {
    fun toMeaning() : Meaning {
        return Meaning(
            definitions = definitions.map { it.toDefinition() },
            partOfSpeech = partOfSpeech
        )
    }
}