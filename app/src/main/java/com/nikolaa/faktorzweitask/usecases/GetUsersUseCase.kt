package com.nikolaa.faktorzweitask.usecases

import com.nikolaa.faktorzweitask.models.UserModel
import com.nikolaa.faktorzweitask.repository.UserRepository
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(forceRefresh: Boolean = false): List<UserModel?> {
        return repository.getAllUsers(forceRefresh)
    }
}