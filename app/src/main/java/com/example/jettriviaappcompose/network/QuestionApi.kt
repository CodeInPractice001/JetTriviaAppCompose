package com.example.jettriviaappcompose.network

import com.example.jettriviaappcompose.model.Question
import retrofit2.http.GET
import javax.inject.Singleton
@Singleton
interface QuestionApi {
@GET("world.json")
suspend fun getAllQuestion(): Question

}