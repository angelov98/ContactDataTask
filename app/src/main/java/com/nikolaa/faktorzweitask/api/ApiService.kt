package com.nikolaa.faktorzweitask.api

import com.nikolaa.faktorzweitask.models.UserModel
import com.nikolaa.faktorzweitask.util.ApiConstants.USERS_BY_ID_ENDPOINT
import com.nikolaa.faktorzweitask.util.ApiConstants.USERS_BY_ID_PATH
import com.nikolaa.faktorzweitask.util.ApiConstants.USERS_ENDPOINT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {
    @GET(USERS_ENDPOINT)
    suspend fun getAllUsers(): Response<List<UserModel>>

    @GET(USERS_ENDPOINT + USERS_BY_ID_ENDPOINT)
    suspend fun getUserById(@Path(USERS_BY_ID_PATH) id: Int): Response<UserModel>
}