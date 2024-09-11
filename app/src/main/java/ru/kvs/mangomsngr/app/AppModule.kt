package ru.kvs.mangomsngr.app

import android.content.Context
import android.net.http.NetworkException
import android.util.Log
import androidx.room.Room
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.kvs.mangomsngr.data.local.AppDatabase
import ru.kvs.mangomsngr.data.local.DB_NAME
import ru.kvs.mangomsngr.data.local.user.TokensTableDao
import ru.kvs.mangomsngr.data.local.user.UserTableDao
import ru.kvs.mangomsngr.data.remote.user.UserRemoteData
import ru.kvs.mangomsngr.data.remote.user.UserService
import ru.kvs.mangomsngr.models.ResponseError
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_MANGO_URL = "https://plannerok.ru/"

    @Provides
    fun providesMangoUrl() = BASE_MANGO_URL

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(9, TimeUnit.SECONDS)
            .readTimeout(9, TimeUnit.SECONDS)
            .writeTimeout(9, TimeUnit.SECONDS)
            .addInterceptor(Interceptor { chain ->
                val response = chain.proceed(chain.request())
                if (!response.isSuccessful) {
                    Log.e("KVS_DEBUG", response.body.toString())
                }
                response
            }).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideEnterService(retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun provideEnterRemoteData(userService: UserService): UserRemoteData =
        UserRemoteData(userService)

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room
            .databaseBuilder(appContext, AppDatabase::class.java, DB_NAME)
            .build()
    }

    @Singleton
    @Provides
    fun provideUserTableDao(database: AppDatabase) = database.getUserDao()

    @Singleton
    @Provides
    fun provideTokensTableDao(database: AppDatabase) = database.getTokensDao()

}