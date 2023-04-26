package com.kongpf8848.dmail.http

import com.kongpf8848.dmail.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitManager private constructor() {
    protected var mOkHttpClient: OkHttpClient? = null
    private var mRetrofit: Retrofit? = null
    var apiInterface: DMApiInterface? = null
       private set

    private object InstanceHolder {
        val instance = RetrofitManager()
    }

    init {
        initOkHttp()
    }

    private fun initOkHttp() {
        //新建log拦截器
        val loggingInterceptor = FixHttpLoggingInterceptor()
        loggingInterceptor.level =
            if (BuildConfig.DEBUG) FixHttpLoggingInterceptor.Level.BODY else FixHttpLoggingInterceptor.Level.NONE
        val builder = OkHttpClient.Builder().apply {
            connectTimeout(15, TimeUnit.SECONDS)
            readTimeout(15, TimeUnit.SECONDS)
            writeTimeout(15, TimeUnit.SECONDS)
            addInterceptor(loggingInterceptor)
        }
        mOkHttpClient = builder.build()
        mRetrofit = Retrofit.Builder()
            .baseUrl("http://example.com/")
            .client(mOkHttpClient!!)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().apply {
                apiInterface = create(DMApiInterface::class.java)
            }
    }

    companion object {
        val instance: RetrofitManager
            get() = InstanceHolder.instance
    }
}