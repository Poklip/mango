package ru.kvs.mangomsngr.app

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.kvs.mangomsngr.data.user.UserRemoteData
import ru.kvs.mangomsngr.data.user.UserService
import javax.inject.Singleton


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
}