package com.appdevelopment.dictionary.features_dictionary.domain.model

data class Meaning(
    val definitions: List<Definition>,
    val partOfSpeech: String,
)
