package com.n1rocket.harrypotterapp.datasource

import com.n1rocket.harrypotterapp.model.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RestDataSource {
    //Base URL: http://hp-api.herokuapp.com/api/
    //characters/house/gryffindor
    @GET("characters/house/{house}")
    suspend fun getCharactersFromHouse(@Path("house") house: String): List<CharacterResponse>
}