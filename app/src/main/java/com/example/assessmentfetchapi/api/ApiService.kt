package com.example.assessmentfetchapi.api

import com.example.assessmentfetchapi.model.UserModelItem
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    companion object{
        const val BASE_URL = "https://fake-json-api.mock.beeceptor.com"
    }

    @GET("/users")
    suspend fun getUsersFromApi() : Response<List<UserModelItem>>
}