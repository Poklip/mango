package ru.kvs.mangomsngr.data.local.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.kvs.mangomsngr.data.local.entities.ProfileEntity
import ru.kvs.mangomsngr.data.local.entities.TokensEntity

@Dao
interface AppDBDao {

    @Insert(entity = TokensEntity::class)
    fun saveProfile(tokensEntity: TokensEntity)

    @Query("SELECT access_token FROM user_tokens;")
    fun getAccessToken(): String?

    @Query("SELECT refresh_token FROM user_tokens;")
    fun getRefreshToken(): String?

    @Query("UPDATE user_tokens SET access_token = :accessToken;")
    fun updateAccessToken(accessToken: String)

    @Insert(entity = ProfileEntity::class)
    fun saveProfile(profile: ProfileEntity)

    @Query("SELECT * FROM user_profile;")
    fun getProfile(): ProfileEntity?

    @Query("DELETE FROM user_profile WHERE id = :userId;")
    fun deleteProfile(userId: Int)

}