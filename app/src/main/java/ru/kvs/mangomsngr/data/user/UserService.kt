package ru.kvs.mangomsngr.data.user

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import ru.kvs.mangomsngr.models.user.ProfileChangedResponse
import ru.kvs.mangomsngr.models.user.CheckAuthBody
import ru.kvs.mangomsngr.models.user.CheckAuthResponse
import ru.kvs.mangomsngr.models.user.Profile
import ru.kvs.mangomsngr.models.user.ProfileToChangeBody
import ru.kvs.mangomsngr.models.user.RegistrationBody
import ru.kvs.mangomsngr.models.user.RegistrationResponse
import ru.kvs.mangomsngr.models.user.SendAuthBody
import ru.kvs.mangomsngr.models.user.SendAuthResponse

interface UserService {

    @POST("api/v1/users/send-auth-code")
    suspend fun sendAuthCode(
        @Body body: SendAuthBody
    ): Response<SendAuthResponse>

    @POST("api/v1/users/check-auth-code")
    suspend fun checkAuthCode(
        @Body body: CheckAuthBody
    ): Response<CheckAuthResponse>

    @POST("api/v1/users/register")
    suspend fun registerNewUser(
        @Body body: RegistrationBody
    ): Response<RegistrationResponse>

    @GET("api/v1/users/me")
    suspend fun getUserData(): Response<Profile>

    @PUT("api/v1/users/me")
    suspend fun changeUserData(
        @Body body: ProfileToChangeBody
    ): Response<ProfileChangedResponse>
}