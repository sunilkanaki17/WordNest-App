package com.appdevelopment.dictionary.features_dictionary.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.appdevelopment.dictionary.features_dictionary.data.util.JsonParser
import com.appdevelopment.dictionary.features_dictionary.domain.model.Meaning
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser
) {
    //Converts JSON string back to a list of Meaning objects when reading from DB.
    @TypeConverter
    fun fromMeaningsJson(json : String): List<Meaning> {
        return jsonParser.fromJson<ArrayList<Meaning>>(
            json,
            object : TypeToken<ArrayList<Meaning>>(){}.type
        )?: emptyList()
    }
    //Converts list of Meaning into a JSON string when writing to DB.
    @TypeConverter
    fun toMeaningsJson(meanings: List<Meaning>) : String {
        return jsonParser.toJson(
            meanings,
            object : TypeToken<ArrayList<Meaning>>(){}.type
        )?: "[]"
    }

}