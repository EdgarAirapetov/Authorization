package com.test.authorization.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.test.authorization.R
import com.test.authorization.api.ApiClientRepository
import com.test.authorization.api.entity.request.LoginRequest
import com.test.authorization.api.entity.response.LoginResponse
import com.test.authorization.utils.PreferenceManager
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class AuthorizationViewModel(application: Application) : AndroidViewModel(application) {

    private val preferenceManager: PreferenceManager = PreferenceManager(application)
    private var repository = ApiClientRepository.getInstance(preferenceManager)
    var loginLiveData: MutableLiveData<LoginResponse> = MutableLiveData()
    var errorLiveData: MutableLiveData<String?> = MutableLiveData()

    fun login(loginRequest: LoginRequest) {
        viewModelScope.launch {
            try {
                val request =
                    repository.login(loginRequest)

                val body = request.body()
                if (body == null) {
                    errorLiveData.postValue(getString(R.string.unknown_authorize_error))
                    return@launch
                }

                if (request.isSuccessful) {
                    loginLiveData.postValue(body)
                } else {
                    if (body.error.isNullOrEmpty()) {
                        errorLiveData.postValue(getString(R.string.unknown_authorize_error))
                        return@launch
                    }

                    val errorTxt = body.error.plus("\n").plus(body.errorDescription ?: "")
                    errorLiveData.postValue(errorTxt)
                }
            } catch (eU: UnknownHostException) {
                errorLiveData.postValue(getString(R.string.server_unavailable))
                eU.stackTrace
            } catch (e: Exception) {
                errorLiveData.postValue(getString(R.string.unknown_error))
                e.stackTrace
            }
        }
    }

    private fun getString(resId: Int) : String {
        return getApplication<Application>().resources.getString(resId)
    }
}