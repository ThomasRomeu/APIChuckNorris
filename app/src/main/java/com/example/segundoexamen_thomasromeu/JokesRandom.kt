package com.example.segundoexamen_thomasromeu

import com.google.gson.annotations.SerializedName

data class JokesRandom (
    val id: String,
    @SerializedName("value") val jokes: String
)

data class JokesCategories (
    val categories: Map<String, List<String>>
)
