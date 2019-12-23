package com.softtehnica.freya.apiManager

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.softtehnica.freya.configs.ServerConnection
import com.softtehnica.freya.models.ApiResponseModel
import com.softtehnica.freya.sessionManager.SessionManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import com.softtehnica.freya.models.Owner
import com.softtehnica.freya.models.PayloadOwner
import com.softtehnica.freya.models.PayloadUsers

enum class FreyaApiStatus { LOADING, ERROR, DONE }
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl("https://www.dummy.test")
    .build()

interface ApiService {
    @GET
    fun getOwnerDetails(@Url url:String, @Header("ApiKey") apiKey: String): Deferred<PayloadOwner>

    @GET
    fun getOwnerUsers(@Url url:String, @Header("ApiKey") apiKey: String, @QueryMap options: HashMap<String, Any>): Deferred<PayloadUsers>
}

object ApiManager {
    val RETROFIT: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
