package com.example.keepnotes.di

import com.example.keepnotes.data.network.NoteApiService
import com.example.keepnotes.data.network.OpenAIService
import com.example.keepnotes.data.network.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {



    @Provides
    @Singleton
    fun provideNoteApiService(): NoteApiService {
        return Retrofit.Builder()
            .baseUrl("https://your-api-base-url.com/") // Replace with your API base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NoteApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideOpenAIService(): OpenAIService {
        return OpenAIService()
    }


    @Provides
    @Singleton
    fun provideRemoteDataSource(apiService: NoteApiService): RemoteDataSource {
        return RemoteDataSource(apiService)
    }

}
