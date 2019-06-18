package com.lounah.core.di

import com.lounah.core.network.networkclient.DateAdapter
import com.lounah.core.network.networkclient.NetworkClient

val posterNetworkModule = module {
    single { NetworkClient.provideApiService(get(), get()) }
    single { NetworkClient.provideOkHttpClient() }
    single { NetworkClient.provideGson(get()) }
    single { DateAdapter() }
}