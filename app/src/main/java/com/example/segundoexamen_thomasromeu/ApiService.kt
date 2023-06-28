package com.example.segundoexamen_thomasromeu

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET(value = "random")
    suspend fun getRandomJoke(): Response<JokesRandom>

    @GET
    suspend fun getCategories(@Url url: String): Response<List<String>>

    @GET
    suspend fun getRandomJokeByCategory(@Url url: String): Response<JokesRandom>
}