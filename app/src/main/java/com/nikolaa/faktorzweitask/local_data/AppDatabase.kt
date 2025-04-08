package com.nikolaa.faktorzweitask.local_data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nikolaa.faktorzweitask.local_data.dao.UserDao
import com.nikolaa.faktorzweitask.local_data.entity.User
import com.nikolaa.faktorzweitask.util.Converters

@Database(entities = [User::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}