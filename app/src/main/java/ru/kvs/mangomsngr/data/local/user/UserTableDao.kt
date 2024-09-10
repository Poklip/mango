package ru.kvs.mangomsngr.data.local.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.kvs.mangomsngr.data.local.entities.ProfileEntity
import ru.kvs.mangomsngr.data.local.entities.TokensEntity

@Dao
interface UserTableDao {

    @Insert(entity = ProfileEntity::class)
    fun saveProfile(profile: ProfileEntity)

    @Query("SELECT * FROM user_profile;")
    fun getProfile(): ProfileEntity?

    @Query("DELETE FROM user_profile WHERE id = :userId;")
    fun deleteProfile(userId: Int)

}