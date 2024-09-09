package ru.kvs.mangomsngr.data.remote.user

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import ru.kvs.mangomsngr.models.user.CheckAuthBody
import ru.kvs.mangomsngr.models.user.CheckAuthResponse
import ru.kvs.mangomsngr.models.user.Profile
import ru.kvs.mangomsngr.models.user.ProfileChangedResponse
import ru.kvs.mangomsngr.models.user.ProfileToChangeBody
import ru.kvs.mangomsngr.models.user.RefreshToken
import ru.kvs.mangomsngr.models.user.RefreshedToken
import ru.kvs.mangomsngr.models.user.RegistrationBody
import ru.kvs.mangomsngr.models.user.RegistrationResponse
import ru.kvs.mangomsngr.models.user.SendAuthBody
import ru.kvs.mangomsngr.models.user.SendAuthResponse

interface UserService {

    @POST("api/v1/users/send-auth-code/")
    suspend fun sendAuthCode(
        @Body body: SendAuthBody
    ): Response<SendAuthResponse>

    @POST("api/v1/users/check-auth-code/")
    suspend fun checkAuthCode(
        @Body body: CheckAuthBody
    ): Response<CheckAuthResponse>

    @POST("api/v1/users/register/")
    suspend fun registerNewUser(
        @Body body: RegistrationBody
    ): Response<RegistrationResponse>

    @GET("api/v1/users/me/")
    suspend fun getUserData(
        @Header("Authorization") accessToken: String
    ): Response<Profile>

    @PUT("api/v1/users/me/")
    suspend fun changeUserData(
        @Header("Authorization") accessToken: String,
        @Body body: ProfileToChangeBody
    ): Response<ProfileChangedResponse>

    @POST("api/v1/users/refresh-token/")
    suspend fun refreshToken(
        @Header("Authorization") accessToken: String,
        @Body body: RefreshToken
    ): Response<RefreshedToken>

    @GET("api/v1/users/check-jwt/")
    suspend fun checkJwt(
        @Header("Authorization") accessToken: String,
    ): Response<String>
}