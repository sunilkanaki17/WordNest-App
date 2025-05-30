package com.appdevelopment.dictionary.features_dictionary.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.appdevelopment.dictionary.features_dictionary.data.local.entity.WordInfoEntity

@Dao
interface WordInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWordInfos(infos: List<WordInfoEntity>)

    @Query(value = "DELETE FROM wordinfoentity where word IN(:words)")
    suspend fun deleteWordInfos(words: List<String>)

    @Query("SELECT * FROM wordinfoentity WHERE word LIKE '%' || :word || '%'")
    suspend fun getWordInfos(word: String) : List<WordInfoEntity>
}

//@Insert ➝ Add new data.
//
//@Query ➝ Read / Search / Delete / Update with SQL.

//% cat % means “anything containing 'cat'”.   (cat, scatter, catalog)