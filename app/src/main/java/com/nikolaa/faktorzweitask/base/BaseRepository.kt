package com.nikolaa.faktorzweitask.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikolaa.faktorzweitask.api.SafeApiRequest
import retrofit2.Response

open class BaseRepository : SafeApiRequest() {

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    override fun exception(errorMsg: String, isUnauthorized: Boolean) {
        _errorMessage.value = errorMsg
    }

    suspend fun <T : Any> fetchData(call: suspend () -> Response<T>): T? {
        try {
            val response = call()
            if (response.isSuccessful) {
                return response.body()
            } else {
                _errorMessage.value = "Error: ${response.code()} ${response.message()}"
                return null
            }
        } catch (e: Exception) {
            _errorMessage.value = "Exception: ${e.message}"
            return null
        }
    }
}