package ru.kvs.mangomsngr.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.kvs.mangomsngr.data.local.user.ProfileDao
import ru.kvs.mangomsngr.data.local.user.ProfileEntity

const val DB_NAME = "appDatabase.db"

@Database(
    version = 1,
    entities = [
        ProfileEntity::class
    ],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getProfileDao(): ProfileDao

}