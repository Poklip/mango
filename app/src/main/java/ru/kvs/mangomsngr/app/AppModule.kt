package ru.kvs.mangomsngr.app

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.kvs.mangomsngr.data.local.AppDatabase
import ru.kvs.mangomsngr.data.local.DB_NAME
import ru.kvs.mangomsngr.data.remote.user.UserRemoteData
import ru.kvs.mangomsngr.data.remote.user.UserService
import javax.inject.Singleton
import kotlin.coroutines.coroutineContext

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_MANGO_URL = "https://plannerok.ru/"

    @Provides
    fun providesMangoUrl() = BASE_MANGO_URL

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    @Provides
    @Singleton
    fun provideEnterService(retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun provideEnterRemoteData(userService: UserService): UserRemoteData =
        UserRemoteData(userService)

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room
            .databaseBuilder(appContext, AppDatabase::class.java, DB_NAME)
            .build()
    }

    @Provides
    @Singleton
    fun provideYourDao(database: AppDatabase) = database.getProfileDao()
}