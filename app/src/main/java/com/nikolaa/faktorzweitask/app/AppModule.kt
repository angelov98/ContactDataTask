package com.nikolaa.faktorzweitask.app

import android.app.Application
import androidx.room.Room
import com.nikolaa.faktorzweitask.api.ApiService
import com.nikolaa.faktorzweitask.api.ApiServiceImpl
import com.nikolaa.faktorzweitask.api.NetworkConnectionInterceptor
import com.nikolaa.faktorzweitask.local_data.AppDatabase
import com.nikolaa.faktorzweitask.local_data.dao.UserDao
import com.nikolaa.faktorzweitask.util.DatabaseConstants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService(
        networkConnectionInterceptor: NetworkConnectionInterceptor
    ): ApiService {
        return ApiServiceImpl(networkConnectionInterceptor)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserCacheDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }
}