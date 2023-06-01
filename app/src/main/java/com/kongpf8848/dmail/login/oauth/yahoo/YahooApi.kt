package com.kongpf8848.dmail.login.oauth.yahoo

import retrofit2.http.GET
import com.kongpf8848.dmail.login.oauth.AuthorizationHeader
import com.kongpf8848.dmail.login.oauth.yahoo.YahooProfileResponse
import io.reactivex.Single
import retrofit2.http.Header

interface YahooApi {
    @GET("/openid/v1/userinfo")
    fun profile(@Header("Authorization") header: AuthorizationHeader): Single<YahooProfileResponse>
}