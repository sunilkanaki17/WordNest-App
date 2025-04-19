package com.appdevelopment.dictionary.features_dictionary.di

import android.app.Application
import androidx.room.Room
import com.appdevelopment.dictionary.features_dictionary.data.local.Converters
import com.appdevelopment.dictionary.features_dictionary.data.local.WordInfoDatabase
import com.appdevelopment.dictionary.features_dictionary.data.remote.DictionaryApi
import com.appdevelopment.dictionary.features_dictionary.data.repository.WordInfoRepositoryImpl
import com.appdevelopment.dictionary.features_dictionary.data.util.GsonParser
import com.appdevelopment.dictionary.features_dictionary.domain.repository.WordInfoRepository
import com.appdevelopment.dictionary.features_dictionary.domain.use_case.GetWordInfo
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


//App needs ViewModel
//⬇
//ViewModel needs GetWordInfo
//⬇
//GetWordInfo needs Repository
//⬇
//Repository needs Room + Retrofit
//⬇
//This Module tells Hilt how to create all of them!

@Module     //This is a file where you tell Hilt how to build things
@InstallIn(SingletonComponent::class)     //Says: “Use this setup for the entire app”
object WordInfoModule {

    @Provides     //This function gives an object to Hilt
    @Singleton
    fun provideGetWordInfoUseCase(repository: WordInfoRepository): GetWordInfo {
        return GetWordInfo(repository)
    }

    @Provides
    @Singleton
    fun provideWordInfoRepository(
        db: WordInfoDatabase,
        api: DictionaryApi
    ): WordInfoRepository {
        return WordInfoRepositoryImpl(api, db.dao)
    }

    @Provides
    @Singleton
    fun provideWordInfoDatabase(app: Application): WordInfoDatabase {
        return Room.databaseBuilder(
            app, WordInfoDatabase::class.java, "word_db"
        ).addTypeConverter(Converters(GsonParser(Gson())))
            .build()
    }

    @Provides
    @Singleton
    fun provideDictionaryApi(): DictionaryApi {
        return Retrofit.Builder()
            .baseUrl(DictionaryApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryApi::class.java)
    }
}