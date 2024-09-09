package ru.kvs.mangomsngr.data.local.user

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kvs.mangomsngr.models.user.ProfileData
import javax.inject.Inject

class ProfileRepoImpl @Inject constructor(private val profileDao: ProfileDao) : ProfileRepo {

    override suspend fun saveProfile(profile: ProfileData) {
        withContext(Dispatchers.IO) {
            profileDao.saveProfile(profile.toEntity())
        }
    }

    override suspend fun getProfile(): ProfileData? {
        return withContext(Dispatchers.IO) {
            profileDao.getProfile()?.toDomain()
        }
    }

    override suspend fun deleteProfile(userId: Int) {
        withContext(Dispatchers.IO) {
            profileDao.deleteProfile(userId)
        }
    }

    override suspend fun editName(name: String) {
        withContext(Dispatchers.IO) {
            profileDao.editName(name)
        }
    }

    override suspend fun editBirthday(birthday: String) {
        withContext(Dispatchers.IO) {
            profileDao.editBirthday(birthday)
        }
    }

    override suspend fun editCity(city: String) {
        withContext(Dispatchers.IO) {
            profileDao.editCity(city)
        }
    }

    override suspend fun editVk(vk: String) {
        withContext(Dispatchers.IO) {
            profileDao.editVk(vk)
        }
    }

    override suspend fun editInstagram(instagram: String) {
        withContext(Dispatchers.IO) {
            profileDao.editInstagram(instagram)
        }
    }

    override suspend fun editStatus(status: String) {
        withContext(Dispatchers.IO) {
            profileDao.editStatus(status)
        }
    }

    override suspend fun editAvatar(avatar: String) {
        withContext(Dispatchers.IO) {
            profileDao.editAvatar(avatar)
        }
    }

    override suspend fun editCompletedTask(completedTask: Int) {
        withContext(Dispatchers.IO) {
            profileDao.editCompletedTask(completedTask)
        }
    }
}