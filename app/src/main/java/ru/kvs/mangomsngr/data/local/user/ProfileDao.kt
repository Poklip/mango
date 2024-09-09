package ru.kvs.mangomsngr.data.local.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProfileDao {

    @Insert(entity = ProfileEntity::class)
    fun saveProfile(profile: ProfileEntity)

    @Query("SELECT * FROM user_profile;")
    fun getProfile(): ProfileEntity?

    @Query("DELETE FROM user_profile WHERE id = :userId;")
    fun deleteProfile(userId: Int)

    @Query("UPDATE user_profile SET name = :name;")
    fun editName(name: String)

    @Query("UPDATE user_profile SET birthday = :birthday;")
    fun editBirthday(birthday: String)

    @Query("UPDATE user_profile SET city = :city;")
    fun editCity(city: String)

    @Query("UPDATE user_profile SET vk = :vk;")
    fun editVk(vk: String)

    @Query("UPDATE user_profile SET instagram = :instagram;")
    fun editInstagram(instagram: String)

    @Query("UPDATE user_profile SET status = :status;")
    fun editStatus(status: String)

    @Query("UPDATE user_profile SET avatar = :avatar;")
    fun editAvatar(avatar: String)

    @Query("UPDATE user_profile SET completed_task = :completedTask;")
    fun editCompletedTask(completedTask: Int)

}