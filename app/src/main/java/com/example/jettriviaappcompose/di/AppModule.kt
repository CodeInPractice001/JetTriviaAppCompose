package com.example.jettriviaappcompose.di

import com.example.jettriviaappcompose.network.QuestionApi
import com.example.jettriviaappcompose.repository.QuestionRepository
import com.example.jettriviaappcompose.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApiService():QuestionApi {
       return Retrofit.Builder()
           .baseUrl(Constants.BASE_URL)
           .addConverterFactory(GsonConverterFactory.create())
           .build()
           .create(QuestionApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRepository(api:QuestionApi):QuestionRepository
    = QuestionRepository(api)

}