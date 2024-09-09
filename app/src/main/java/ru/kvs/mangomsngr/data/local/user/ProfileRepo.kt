package ru.kvs.mangomsngr.data.local.user

import ru.kvs.mangomsngr.models.user.ProfileData

interface ProfileRepo {

    suspend fun saveProfile(profile: ProfileData)

    suspend fun getProfile(): ProfileData?

    suspend fun deleteProfile(userId: Int)

    suspend fun editName(name: String)

    suspend fun editBirthday(birthday: String)

    suspend fun editCity(city: String)

    suspend fun editVk(vk: String)

    suspend fun editInstagram(instagram: String)

    suspend fun editStatus(status: String)

    suspend fun editAvatar(avatar: String)

    suspend fun editCompletedTask(completedTask: Int)

}