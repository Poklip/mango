package ru.kvs.mangomsngr.data.local.user

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kvs.mangomsngr.data.local.entities.TokensEntity
import ru.kvs.mangomsngr.models.user.ProfileData
import javax.inject.Inject

class UserLocalRepo @Inject constructor(private val userTableDao: UserTableDao, private val tokensTableDao: TokensTableDao) {

    suspend fun saveTokens(accessToken: String, refreshToken: String) {
        withContext(Dispatchers.IO) {
            tokensTableDao.saveProfile(TokensEntity(accessToken = accessToken, refreshToken = refreshToken))
        }
    }

    suspend fun getAccessToken(): String? {
        return withContext(Dispatchers.IO) {
            tokensTableDao.getAccessToken()
        }
    }

    suspend fun getRefreshToken(): String? {
        return withContext(Dispatchers.IO) {
            tokensTableDao.getRefreshToken()
        }
    }

    suspend fun updateAccessToken(accessToken: String) {
        return withContext(Dispatchers.IO) {
            tokensTableDao.updateAccessToken(accessToken)
        }
    }

    suspend fun saveProfile(profile: ProfileData) {
        withContext(Dispatchers.IO) {
            userTableDao.saveProfile(profile.toEntity())
        }
    }

    suspend fun getProfile(): ProfileData? {
        return withContext(Dispatchers.IO) {
            userTableDao.getProfile()?.toDomain()
        }
    }

    suspend fun deleteProfile(userId: Int) {
        withContext(Dispatchers.IO) {
            userTableDao.deleteProfile(userId)
        }
    }
}