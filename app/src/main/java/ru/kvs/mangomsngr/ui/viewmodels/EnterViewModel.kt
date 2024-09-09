package ru.kvs.mangomsngr.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kvs.mangomsngr.data.local.user.ProfileRepo
import ru.kvs.mangomsngr.data.remote.user.UserRepo
import ru.kvs.mangomsngr.models.user.CheckAuthBody
import ru.kvs.mangomsngr.models.user.CheckAuthResponse
import ru.kvs.mangomsngr.models.user.RegistrationBody
import ru.kvs.mangomsngr.models.user.RegistrationResponse
import ru.kvs.mangomsngr.models.user.SendAuthBody
import javax.inject.Inject

@HiltViewModel
class EnterViewModel @Inject constructor(private val repository: UserRepo, private val localRepo: ProfileRepo) : ViewModel() {

    private val dbScope = CoroutineScope(Dispatchers.IO)

    fun sendAuth(phoneNumber: String): LiveData<Boolean> {
        return liveData {
            val sendAuthResponse =
                repository.sendAuth(phoneNumberForCode = SendAuthBody(phoneNumber = phoneNumber))
            Log.d("KVS_DEBUG", sendAuthResponse.body().toString())
            sendAuthResponse.body()?.let { emit(it.isSuccess) }
        }
    }

    fun checkAuth(phoneNumber: String, code: String): LiveData<CheckAuthResponse> {
        return liveData {
            val checkAuthResponse = repository.checkAuth(
                phoneNumberWithCode = CheckAuthBody(
                    phoneNumber = phoneNumber,
                    code = code
                )
            )
            checkAuthResponse.body()?.let { emit(it) }
        }
    }

    fun registerNewUser(
        phoneNumber: String,
        name: String,
        username: String
    ): LiveData<RegistrationResponse> {
        return liveData {
            val registerResponse = repository.registerNewUser(
                registerInfo = RegistrationBody(
                    phoneNumber = phoneNumber,
                    name = name,
                    username = username
                )
            )
            registerResponse.body()?.let { emit(it) }
        }
    }

    fun saveTokens(
        accessToken: String,
        refreshToken: String
    ) {
        dbScope.launch {
            localRepo.saveTokens(accessToken, refreshToken)
        }
    }
}