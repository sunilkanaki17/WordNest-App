package com.appdevelopment.dictionary.features_dictionary.data.repository

import retrofit2.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.appdevelopment.dictionary.features_dictionary.data.local.WordInfoDao
import com.appdevelopment.dictionary.features_dictionary.data.remote.DictionaryApi
import com.appdevelopment.dictionary.features_dictionary.domain.model.WordInfo
import com.appdevelopment.dictionary.features_dictionary.domain.repository.WordInfoRepository
import com.plcoding.dictionary.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

//@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class WordInfoRepositoryImpl(
    private val api : DictionaryApi,
    private val dao : WordInfoDao
): WordInfoRepository {

    override fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>>  = flow{
        //loading stage
        emit(Resource.Loading())

        //checks in cache(Room) if there then show
        //Gets a list of database objects (WordInfoEntity)
        //Converts each one to a domain object (WordInfo) using the toWordInfo() function
        val wordinfos = dao.getWordInfos(word).map { it.toWordInfo() }
        emit(Resource.Loading(wordinfos))

        //get it from API and then add to Room
        try {
            val remoteWordInfos = api.getWordInfo(word)
            dao.deleteWordInfos(remoteWordInfos.map { it.word })  //this only take word
            dao.insertWordInfos(remoteWordInfos.map { it.toWordinfoEntity() })  //Converts each API object to a WordInfoEntity (which Room can store)

        }
        catch (e: HttpException) {
            emit(Resource.Error(
                message = "Oops, Word Doesn't Exist !",
                data = wordinfos
            ))
        }
        catch (e: IOException) {
            emit(Resource.Error(
                message = "Couldn't reach server, check your internet connection!",
                data = wordinfos
            ))
        }

        //whatever we got fetch it from Room
        val newWordInfos = dao.getWordInfos(word).map { it.toWordInfo() }
        emit(Resource.Success(newWordInfos))
    }
}