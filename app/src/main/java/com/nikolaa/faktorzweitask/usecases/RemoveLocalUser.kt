package com.nikolaa.faktorzweitask.usecases

import com.nikolaa.faktorzweitask.repository.UserRepository
import javax.inject.Inject

class RemoveLocalUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(userId: Int) {
        repository.deleteUser(userId)
    }
}