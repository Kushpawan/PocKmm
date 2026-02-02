package com.linarc.kmmpoc.domain.repository

import com.linarc.kmmpoc.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun saveUser(user: User)
    fun getUsers(): Flow<List<User>>
}
