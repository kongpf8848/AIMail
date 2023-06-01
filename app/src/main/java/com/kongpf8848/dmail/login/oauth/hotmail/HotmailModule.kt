package com.kongpf8848.dmail.login.oauth.hotmail

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class HotmailModule {

    @Provides
    fun provideHotmailApi(): HotmailApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://apis.live.net/")
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofit.create(HotmailApi::class.java)
    }
}