package com.appdevelopment.dictionary.features_dictionary.presentation

import com.appdevelopment.dictionary.features_dictionary.domain.model.WordInfo

data class WordInfoState(
    val wordInfoItems: List<WordInfo> = emptyList(),
    val isLoading: Boolean = false,
)
