package com.kongpf8848.dmail.login.oauth.yahoo

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class YahooModule {

    @Provides
    fun provideYahooApi(): YahooApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.login.yahoo.com/")
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofit.create(YahooApi::class.java)
    }
}