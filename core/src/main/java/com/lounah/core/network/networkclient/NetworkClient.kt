package com.lounah.core.network.networkclient

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

object NetworkClient {

    private const val CONNECTION_TIMEOUTS_MS = 20000L
    private const val BASE_URL = ""

    fun provideApiService(
        gson: Gson,
        client: OkHttpClient
    ): PosterApiService = Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(PosterApiService::class.java)

    fun provideOkHttpClient(): OkHttpClient {
        val okBuilder = OkHttpClient.Builder().apply {
            connectTimeout(CONNECTION_TIMEOUTS_MS, TimeUnit.MILLISECONDS)
            readTimeout(CONNECTION_TIMEOUTS_MS, TimeUnit.MILLISECONDS)
            writeTimeout(CONNECTION_TIMEOUTS_MS, TimeUnit.MILLISECONDS)
        }

        return okBuilder.build()
    }

    fun provideGson(dateAdapter: DateAdapter): Gson {
        return GsonBuilder()
                .registerTypeAdapter(Date::class.java, dateAdapter)
                .create()
    }
}