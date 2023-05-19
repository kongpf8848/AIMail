package com.kongpf8848.dmail.oauth.yahoo;

import com.kongpf8848.dmail.oauth.hotmail.HotmailApi;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class YahooModule {

    public static YahooApi provideYahooApi() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.login.yahoo.com/")
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(YahooApi.class);
    }
}
