package ru.kvs.mangomsngr.data.local.user

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kvs.mangomsngr.data.local.entities.TokensEntity
import ru.kvs.mangomsngr.models.user.ProfileData
import javax.inject.Inject

class UserLocalRepo @Inject constructor(private val userTableDao: UserTableDao, private val tokensTableDao: TokensTableDao) {

    suspend fun saveTokens(accessToken: String, refreshToken: String) {
        withContext(Dispatchers.IO) {
            tokensTableDao.saveTokens(TokensEntity(accessToken = accessToken, refreshToken = refreshToken))
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
        Log.i("KVS_DEBUG", "profile saved: $profile")
        withContext(Dispatchers.IO) {
            userTableDao.saveProfile(profile.toEntity())
        }
    }

    suspend fun getProfile(): ProfileData? {
        return withContext(Dispatchers.IO) {
            userTableDao.getProfile()?.toDomain()
        }
    }

    suspend fun updateProfile(profile: ProfileData) {
        withContext(Dispatchers.IO) {
            userTableDao.updateProfile(
                name = profile.name,
                birthday = profile.birthday,
                city = profile.city,
                vk = profile.vk,
                instagram = profile.instagram,
                status = profile.status
            )
        }
    }

    suspend fun deleteProfile() {
        withContext(Dispatchers.IO) {
            userTableDao.deleteProfile()
        }
    }
}