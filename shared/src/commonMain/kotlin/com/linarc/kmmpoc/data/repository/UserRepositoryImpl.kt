package com.linarc.kmmpoc.data.repository

import com.linarc.kmmpoc.data.local.UserDao
import com.linarc.kmmpoc.data.local.UserEntity
import com.linarc.kmmpoc.domain.model.User
import com.linarc.kmmpoc.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {
    override suspend fun saveUser(user: User) {
        userDao.insertUser(
            UserEntity(
                username = user.username,
                token = user.token,
                role = user.role
            )
        )
    }

    override fun getUsers(): Flow<List<User>> {
        return userDao.getAllUsers().map { entities ->
            entities.map {
                User(
                    username = it.username,
                    token = it.token,
                    role = it.role
                )
            }
        }
    }
}
