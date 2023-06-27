package com.example.segundoexamen_thomasromeu

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET(value = "random")
    suspend fun getRandomJoke(): Response<JokesRandom>
}