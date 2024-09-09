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

    suspend fun editName(name: String) {
        withContext(Dispatchers.IO) {
            appDBDao.editName(name)
        }
    }

    suspend fun editBirthday(birthday: String) {
        withContext(Dispatchers.IO) {
            appDBDao.editBirthday(birthday)
        }
    }

    suspend fun editCity(city: String) {
        withContext(Dispatchers.IO) {
            appDBDao.editCity(city)
        }
    }

    suspend fun editVk(vk: String) {
        withContext(Dispatchers.IO) {
            appDBDao.editVk(vk)
        }
    }

    suspend fun editInstagram(instagram: String) {
        withContext(Dispatchers.IO) {
            appDBDao.editInstagram(instagram)
        }
    }

    suspend fun editStatus(status: String) {
        withContext(Dispatchers.IO) {
            appDBDao.editStatus(status)
        }
    }

    suspend fun editAvatar(avatar: String) {
        withContext(Dispatchers.IO) {
            appDBDao.editAvatar(avatar)
        }
    }

    suspend fun editCompletedTask(completedTask: Int) {
        withContext(Dispatchers.IO) {
            appDBDao.editCompletedTask(completedTask)
        }
    }
}