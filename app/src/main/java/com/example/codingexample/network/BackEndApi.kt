package com.example.codingexample.network

import com.example.codingexample.models.PixabayModel
import retrofit2.Response
import retrofit2.http.*

interface BackEndApi {

   // https://pixabay.com/api/?key=25825065-1d2f674ec781f52c81d1c162b&q=yellow+flowers&image_type=photo&pretty=true

    @GET("/api")
    suspend fun callPixabayApi(
        @Query("key") key: String,
        @Query("image_type") image_type: String,
        @Query("pretty") pretty: Boolean
    ): Response<PixabayModel>
}

