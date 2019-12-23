package com.softtehnica.freya.fragments.lock

import android.app.Application
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.*
import com.softtehnica.freya.apiManager.ApiManager
import com.softtehnica.freya.configs.ServerConnection
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class LockViewModel(app: Application) : AndroidViewModel(app) {

    private var viewModelJob = Job()
    private val ioScope = CoroutineScope(Dispatchers.IO + viewModelJob)
    private val timer = Timer()
    private val app = app
    private val _timeText = MutableLiveData<Long>()
    val timeText: LiveData<Long>
        get() = _timeText

    init {
        val timerTask = object : TimerTask() {
            override fun run() {
                 val now = System.currentTimeMillis()
                _timeText.postValue(now)
            }
        }
        timer.schedule(timerTask, 0, 1000)
    }

    fun getusers(){
        ioScope.launch {
            val serverConn = ServerConnection(app.applicationContext)
            val date1970 = Date(0)

            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            var lastSyncDate = sdf.format(date1970)
            lastSyncDate = lastSyncDate.replace(" ", "T")
            Log.i("GETUsers", "${lastSyncDate}")
            val parameters = HashMap<String, Any>()
            parameters.put("pageNo", "1")
            parameters.put("top", "10")
            parameters.put("referenceDate", lastSyncDate)
            val url = serverConn.serverUrl + "/User/FindManyUnsynced"
            val userDeferred =  ApiManager.RETROFIT.getOwnerUsers(url, serverConn.apiKey, parameters)
            try {
               // _status.value = WandrApiStatus.LOADING
                val payloadUsers = userDeferred.await()

                Log.i("GETUsers", "THERE ARE ${payloadUsers.payload.up.records.size} users for this owner")
                //_status.value = WandrApiStatus.DONE
                //_languages.value = listResult.items

            }
            catch (e: Exception) {
//                _status.value = WandrApiStatus.ERROR
//                _error.value = e.message
//                _status.value = "Failure: ${e.message}"

                Log.i("GETUsers", "${e.message}")
            }
            catch (ex: HttpException){
//                _error.value = ex.response().message() + ex.response().errorBody()?.string()
                Log.i("GETUsers", "${ex.response().message() + ex.response().errorBody()?.string()}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
        viewModelJob.cancel()
    }
}
