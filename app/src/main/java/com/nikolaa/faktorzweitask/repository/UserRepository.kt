package com.nikolaa.faktorzweitask.repository

import com.nikolaa.faktorzweitask.api.ApiService
import com.nikolaa.faktorzweitask.base.BaseRepository
import com.nikolaa.faktorzweitask.local_data.dao.UserDao
import com.nikolaa.faktorzweitask.local_data.entity.User
import com.nikolaa.faktorzweitask.models.AddressModel
import com.nikolaa.faktorzweitask.models.CompanyModel
import com.nikolaa.faktorzweitask.models.GeoModel
import com.nikolaa.faktorzweitask.models.UserModel
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
) : BaseRepository() {
    suspend fun getAllUsers(forceRefresh: Boolean = false): List<UserModel?> {
        if (forceRefresh) {
            val response = apiService.getAllUsers()
            if (response.isSuccessful) {
                val users = response.body() ?: emptyList()

                userDao.clearUsers()
                userDao.insertUsers(users.map { mapToUserEntity(it) })

                return users
            } else {
                throw Exception("Error fetching users: ${response.errorBody()?.string()}")
            }
        }

        // Check if cached data exists in Room
        val cachedUsers = userDao.getUsers()

        if (cachedUsers.isNotEmpty()) {
            return cachedUsers.map { mapToUserModel(it) }
        }

        // If no cached data, fetch from API
        val response = apiService.getAllUsers()
        if (response.isSuccessful) {
            val users = response.body() ?: emptyList()

            // Cache the fetched users in Room
            userDao.insertUsers(users.map { mapToUserEntity(it) })

            return users
        } else {
            throw Exception("Error fetching users: ${response.errorBody()?.string()}")
        }
    }

    suspend fun getUsersById(userId: Int): UserModel {
        val cachedUser = userDao.getUserById(userId)

        return if (cachedUser != null) {

            mapToUserModel(cachedUser)
        } else {
            val response = apiService.getUserById(userId)

            return if (response.isSuccessful) {
                val user = response.body() ?: throw Exception("User not found")
                userDao.insertUsers(listOf(mapToUserEntity(user)))
                user
            } else {
                throw Exception("Error fetching user: ${response.errorBody()?.string()}")
            }
        }
    }

    suspend fun deleteUser(userId: Int) {
        userDao.deleteUserById(userId)
    }

    private fun mapToUserEntity(userModel: UserModel): User {
        return User(
            id = userModel.id ?: 0,
            name = userModel.name,
            username = userModel.username,
            email = userModel.email,
            phone = userModel.phone,
            website = userModel.website,
            companyName = userModel.company?.name,
            companyCatchPhrase = userModel.company?.catchPhrase,
            companyBs = userModel.company?.bs,
            street = userModel.address?.street,
            suite = userModel.address?.suite,
            city = userModel.address?.city,
            zipcode = userModel.address?.zipcode,
            lat = userModel.address?.geo?.lat,
            lng = userModel.address?.geo?.lng
        )
    }

    private fun mapToUserModel(user: User): UserModel {
        return UserModel(
            id = user.id,
            name = user.name,
            username = user.username,
            email = user.email,
            address = AddressModel(
                street = user.street,
                suite = user.suite,
                city = user.city,
                zipcode = user.zipcode,
                geo = GeoModel(lat = user.lat, lng = user.lng)
            ),
            phone = user.phone,
            website = user.website,
            company = CompanyModel(
                name = user.companyName,
                catchPhrase = user.companyCatchPhrase,
                bs = user.companyBs
            )
        )
    }
}
