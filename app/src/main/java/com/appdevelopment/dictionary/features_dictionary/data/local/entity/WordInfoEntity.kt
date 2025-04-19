package com.appdevelopment.dictionary.features_dictionary.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.appdevelopment.dictionary.features_dictionary.domain.model.Meaning
import com.appdevelopment.dictionary.features_dictionary.domain.model.WordInfo

@Entity   //Local Database storing this is used when api is called it stores in room database
data class WordInfoEntity(
    val word :String,
    val phonetic : String,
    val meanings : List<Meaning>,
    @PrimaryKey val Id:Int? = null
) {
    fun toWordInfo() : WordInfo {
        return WordInfo(
            word = word,
            phonetic = phonetic,
            meanings = meanings
        )
    }
}
