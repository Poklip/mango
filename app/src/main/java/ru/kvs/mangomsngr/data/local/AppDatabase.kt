package ru.kvs.mangomsngr.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.kvs.mangomsngr.data.local.entities.ProfileEntity
import ru.kvs.mangomsngr.data.local.entities.TokensEntity
import ru.kvs.mangomsngr.data.local.user.TokensTableDao
import ru.kvs.mangomsngr.data.local.user.UserTableDao

const val DB_NAME = "appDatabase.db"

@Database(
    version = 1,
    entities = [
        ProfileEntity::class,
        TokensEntity::class
    ],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserTableDao

    abstract fun getTokensDao(): TokensTableDao

}