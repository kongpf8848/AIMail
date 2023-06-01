package com.kongpf8848.dmail.login.oauth.hotmail

import com.kongpf8848.dmail.login.oauth.AuthorizationHeader
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header

interface HotmailApi {
    @GET("/v5.0/me")
    fun profile(@Header("Authorization") header: AuthorizationHeader?): Single<HotmailProfile>
}