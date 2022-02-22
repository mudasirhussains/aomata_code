package com.example.codingexample.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.codingexample.models.PixabayModel
import com.example.codingexample.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    val mPixabayResponse = MutableLiveData<PixabayModel>()
    private var loadingError = MutableLiveData<String?>()
    var loading = MutableLiveData<Boolean>()
    private var job: Job? = null
    private var exceptionalHandling = CoroutineExceptionHandler { _, throwable ->
        onError("Exceptional Error: ${throwable.localizedMessage}")
    }

    private fun onError(message: String) {
        try {
            loading.postValue(true)
            if (message.isNotEmpty()) {
                loadingError.postValue(message)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun getAllImagesFromApi() {
        job = CoroutineScope(Dispatchers.IO + exceptionalHandling).launch {
            val response = mainRepository.getAllImagesFromApi()
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        mPixabayResponse.value = response.body()
                        loadingError.value = null
                        loading.value = false
                    } else {
                        onError("UserLoadError : ${response.message()} ")
                        loading.value = true
                    }
                } catch (e: SocketTimeoutException) {
                    onError("UserLoadError : timeout ")
                    loading.value = true
                }
            }
        }
    }
}