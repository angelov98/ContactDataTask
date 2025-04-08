package com.nikolaa.faktorzweitask.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.nikolaa.faktorzweitask.BuildConfig
import com.nikolaa.faktorzweitask.models.UserModel
import com.nikolaa.faktorzweitask.util.ApiConstants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class ApiServiceImpl(
    private val networkConnectionInterceptor: NetworkConnectionInterceptor
) : ApiService {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(getOkHttpClient(networkConnectionInterceptor))
        .build()

    private val apiService = retrofit.create(ApiService::class.java)

    override suspend fun getAllUsers(): Response<List<UserModel>> {
        return apiService.getAllUsers()
    }

    override suspend fun getUserById(id: Int): Response<UserModel> {
        return apiService.getUserById(id)
    }

    private fun getOkHttpClient(networkConnectionInterceptor: NetworkConnectionInterceptor): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(networkConnectionInterceptor)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .header(ApiConstants.USER_AGENT, ApiConstants.USER_AGENT_VALUE)
                    .header(ApiConstants.ACCEPT, ApiConstants.ACCEPT_VALUE)
                    .build()
                chain.proceed(request)
            }
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }
}