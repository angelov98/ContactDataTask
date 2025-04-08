package com.nikolaa.faktorzweitask.api

import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.nikolaa.faktorzweitask.models.ErrorResponseModel
import org.json.JSONException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

abstract class SafeApiRequest {

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T? {
        try {
            val response = call.invoke()

            if (response.isSuccessful) {
                return response.body()
            } else {
                val error = response.errorBody()?.string()

                error?.let {
                    handleApiError(response.code(), it)
                }
            }

        } catch (e: Exception) {
            handleException(e)
        }
        return null
    }

    private fun handleApiError(code: Int, error: String) {
        try {
            val responseError: ErrorResponseModel? =
                Gson().fromJson(error, ErrorResponseModel::class.java)

            when (code) {
                401, 400, 404 -> {
                    exception("We are sorry something went wrong, please try later.", false)
                }

                else -> {
                    responseError?.message?.let {
                        exception(it, false)
                    } ?: exception("We are sorry something went wrong, please try later.", false)
                }
            }
        } catch (e: Exception) {
            when (e) {
                is JSONException, is JsonSyntaxException, is JsonParseException -> {
                    exception("We are sorry something went wrong, please try later.", false)
                }
            }
        }
    }

    private fun handleException(e: Exception) {
        when (e) {
            is NoInternetException -> {
                exception("No internet connection. Please try again later.", false)
            }

            is SocketTimeoutException, is IOException -> {
                exception("We are sorry something went wrong, please try later.", false)
            }

            else -> {
                exception("Unexpected error: ${e.localizedMessage}", false)
            }
        }
    }

    abstract fun exception(errorMsg: String, isUnauthorized: Boolean)
}
