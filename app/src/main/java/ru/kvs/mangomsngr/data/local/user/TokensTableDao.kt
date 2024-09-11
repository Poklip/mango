package ru.kvs.mangomsngr.data.local.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.kvs.mangomsngr.data.local.entities.TokensEntity

@Dao
interface TokensTableDao {

    @Insert(entity = TokensEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun saveTokens(tokensEntity: TokensEntity)

    @Query("SELECT access_token FROM user_tokens;")
    fun getAccessToken(): String?

    @Query("SELECT refresh_token FROM user_tokens;")
    fun getRefreshToken(): String?

    @Query("UPDATE user_tokens SET access_token = :accessToken;")
    fun updateAccessToken(accessToken: String)

}