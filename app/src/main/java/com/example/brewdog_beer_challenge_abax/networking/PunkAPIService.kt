package com.example.brewdog_beer_challenge_abax.networking

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET


private const val BASE_URL = "https://api.punkapi.com/v2/"
//private const val BASE_URL = "https://api.punkapi.com/v2/beers?page=1"

private val retrofit = Retrofit.Builder().addConverterFactory(ScalarsConverterFactory.create()).baseUrl(BASE_URL).build()

interface PunkAPIService {
    @GET("beers?page=1")
    suspend fun getBeers(): String
}

object BeerAPI {
    val retrofitService: PunkAPIService by lazy{
        retrofit.create(PunkAPIService::class.java)
    }
}