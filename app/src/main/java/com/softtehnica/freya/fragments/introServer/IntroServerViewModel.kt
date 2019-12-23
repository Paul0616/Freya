package com.softtehnica.freya.fragments.introServer

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.softtehnica.freya.apiManager.ApiManager
import com.softtehnica.freya.apiManager.FreyaApiStatus
import com.softtehnica.freya.configs.ServerConnection
import kotlinx.coroutines.*
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException


class IntroServerViewModel(app: Application) : AndroidViewModel(app) {

    private var viewModelJob = Job()
    private val ioScope = CoroutineScope(Dispatchers.IO + viewModelJob)
    val serverConnection = ServerConnection(app.applicationContext)

    val isDemoMode = MutableLiveData<Boolean>()
    val serverUrl = MutableLiveData<String>()
    val apiKey = MutableLiveData<String>()

    private val _keyboardIsOpen = MutableLiveData<Boolean>()
    val keyboardIsOpen: LiveData<Boolean>
        get() = _keyboardIsOpen

    private val _status = MutableLiveData<FreyaApiStatus>()
    val status: LiveData<FreyaApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    init {
        Log.i("IntroServerViewModel", "CREATED")
        loadServerConnectionData()
    }

    private fun loadServerConnectionData() {
        isDemoMode.value = serverConnection.isDemoMode
        if (!serverConnection.isDemoMode) {
            serverUrl.value = serverConnection.serverUrl
            apiKey.value = serverConnection.apiKey
        }
//        Log.i(
//            "IntroServerViewModel",
//            "url: ${serverConnection.serverUrl} apikey: ${serverConnection.apiKey} demoMode: ${serverConnection.isDemoMode} isConfigured: ${serverConnection.isConfigured}"
//        )
    }

    fun resetServerConnection(){
        serverConnection.apiKey = ""
    }
    fun setKeyboardOpenFlag(isOpen: Boolean) {
        _keyboardIsOpen.value = isOpen
    }

    fun checkOwnerCredentials() {
        ioScope.launch {
            val userDeferred = ApiManager.RETROFIT.getOwnerDetails(
                serverConnection.ownerGetDetails,
                serverConnection.apiKey
            )
            try {
                _status.postValue(FreyaApiStatus.LOADING)
                val payloadUsers = userDeferred.await()
                if (payloadUsers.isSuccess)
                    _status.postValue(FreyaApiStatus.DONE)
                else {
                    _status.postValue(FreyaApiStatus.ERROR)
                    _error.postValue(payloadUsers.errorMessage.plus(" 1"))
                }
            }
            catch (e: Exception) {
                if(e is HttpException){
                    try {
                        val errorBody = JSONObject(e.response().errorBody()?.string())
                        _error.postValue(errorBody.getString("ErrorMessage"))
                    } catch (ejson: JSONException) {
                        _error.postValue(e.message)
                    }
                }
                _status.postValue(FreyaApiStatus.ERROR)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        Log.i("IntroServerViewModel", "DESTROYED")
    }
}
