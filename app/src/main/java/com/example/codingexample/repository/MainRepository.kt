package com.example.codingexample.repository

import com.example.codingexample.network.BackEndApi
import com.example.codingexample.utils.Constants
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class MainRepository @Inject constructor(
    private val backEndApi: BackEndApi,
) {
    suspend fun getAllImagesFromApi() =
        backEndApi.callPixabayApi(Constants.API_KEY, Constants.IMAGE_TYPE, true)
}