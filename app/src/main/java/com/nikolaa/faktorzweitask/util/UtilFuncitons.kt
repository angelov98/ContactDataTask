package com.nikolaa.faktorzweitask.util

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.nikolaa.faktorzweitask.models.AddressModel
import com.nikolaa.faktorzweitask.models.CompanyModel

object UtilFunctions {
    //region Api helper functions
    fun checkInternetConnection(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }
    //endregion
}


class Converters {
    @TypeConverter
    fun fromAddressModel(address: AddressModel?): String? {
        return Gson().toJson(address)
    }

    @TypeConverter
    fun toAddressModel(address: String?): AddressModel? {
        return Gson().fromJson(address, AddressModel::class.java)
    }

    @TypeConverter
    fun fromCompanyModel(company: CompanyModel?): String? {
        return Gson().toJson(company)
    }

    @TypeConverter
    fun toCompanyModel(company: String?): CompanyModel? {
        return Gson().fromJson(company, CompanyModel::class.java)
    }
}