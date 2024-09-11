package ru.kvs.mangomsngr

import android.app.Application
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import ru.kvs.mangomsngr.data.local.AppDatabase
import ru.kvs.mangomsngr.data.local.user.UserTableDao
import ru.kvs.mangomsngr.models.user.AvatarExtended
import ru.kvs.mangomsngr.models.user.ProfileData
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.DefaultAsserter.assertEquals

private class MyApp: Application()

@RunWith(AndroidJUnit4::class)
@Config(application = MyApp::class)
class DbTest {
    private lateinit var userDao: UserTableDao
    private lateinit var database: AppDatabase

    @AfterTest
    fun closeDb() {
        database.close()
    }

    @BeforeTest
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        userDao = database.getUserDao()
    }

    @Test
    fun writeUserAndRead() {
        val newProfile = ProfileData(
            name = "kova",
            username = "vedrov",
            isOnline = true,
            phoneNumber = "+79867862634",
            userId = 1,
            avatarExtended = AvatarExtended(
                null,
                "",
                ""
            )
        )
        userDao.saveProfile(newProfile.toEntity())
        val savedProfile = userDao.getProfile()?.toDomain()
        assertEquals(
            message = "user set and get",
            expected = newProfile,
            actual = savedProfile
        )
    }
}