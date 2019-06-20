package com.lounah.core.network.networkclient

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object NetworkClient {

    private const val CONNECTION_TIMEOUTS_MS = 20000L
    private const val BASE_URL = ""

    fun provideApiService(
        moshi: Moshi,
        client: OkHttpClient
    ): PosterApiService = Retrofit.Builder()
        .client(client)
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
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

    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .build()

    }
}