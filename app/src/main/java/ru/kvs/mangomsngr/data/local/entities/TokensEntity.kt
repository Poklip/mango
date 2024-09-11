package ru.kvs.mangomsngr.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_tokens")
data class TokensEntity(
    @ColumnInfo("access_token") val accessToken: String,
    @PrimaryKey
    @ColumnInfo("refresh_token") val refreshToken: String,
)
