package com.nikolaa.faktorzweitask.usecases

import com.nikolaa.faktorzweitask.models.UserModel
import com.nikolaa.faktorzweitask.repository.UserRepository
import javax.inject.Inject

class GetUserDetailsUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(userId: Int): UserModel {
        return repository.getUsersById(userId)
    }
}