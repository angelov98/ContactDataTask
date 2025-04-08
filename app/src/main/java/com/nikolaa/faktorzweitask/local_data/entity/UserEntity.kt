package com.nikolaa.faktorzweitask.local_data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nikolaa.faktorzweitask.util.DatabaseConstants.USER_DB_NAME

@Entity(tableName = USER_DB_NAME)
data class User(
    @PrimaryKey val id: Int,
    val name: String?,
    val username: String?,
    val email: String?,
    val phone: String?,
    val website: String?,

    // Company-related fields
    val companyName: String?,
    val companyCatchPhrase: String?,
    val companyBs: String?,

    // Address-related fields
    val street: String?,
    val suite: String?,
    val city: String?,
    val zipcode: String?,
    val lat: String?,
    val lng: String?
)