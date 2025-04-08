package com.nikolaa.faktorzweitask.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.nikolaa.faktorzweitask.R
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class NetworkConnectionInterceptor @Inject constructor(
    @ApplicationContext private val context: Context
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return try {
            if (!isNetworkAvailable()) {
                val errorMessage = context.getString(R.string.no_network_connection)
                throw NoInternetConnectionException(errorMessage)
            }
            chain.proceed(chain.request())
        } catch (e: Exception) {
            throw e
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}

class NoInternetConnectionException(message: String) : IOException(message)
