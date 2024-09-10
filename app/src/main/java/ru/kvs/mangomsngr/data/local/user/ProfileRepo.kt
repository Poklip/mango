package ru.kvs.mangomsngr.data.local.user

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kvs.mangomsngr.data.local.entities.TokensEntity
import ru.kvs.mangomsngr.models.user.ProfileData
import javax.inject.Inject

class ProfileRepo @Inject constructor(private val appDBDao: AppDBDao) {

    suspend fun saveTokens(accessToken: String, refreshToken: String) {
        appDBDao.saveProfile(TokensEntity(accessToken = accessToken, refreshToken = refreshToken))
    }

    suspend fun getAccessToken(): String? {
        return withContext(Dispatchers.IO) {
            appDBDao.getAccessToken()
        }
    }

    suspend fun getRefreshToken(): String? {
        return withContext(Dispatchers.IO) {
            appDBDao.getRefreshToken()
        }
    }

    suspend fun updateAccessToken(accessToken: String) {
        return withContext(Dispatchers.IO) {
            appDBDao.updateAccessToken(accessToken)
        }
    }

    suspend fun saveProfile(profile: ProfileData) {
        withContext(Dispatchers.IO) {
            appDBDao.saveProfile(profile.toEntity())
        }
    }

    suspend fun getProfile(): ProfileData? {
        return withContext(Dispatchers.IO) {
            appDBDao.getProfile()?.toDomain()
        }
    }

    suspend fun deleteProfile(userId: Int) {
        withContext(Dispatchers.IO) {
            appDBDao.deleteProfile(userId)
        }
    }
}