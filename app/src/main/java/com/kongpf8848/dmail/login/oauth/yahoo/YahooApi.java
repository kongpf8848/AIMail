package com.kongpf8848.dmail.login.oauth.yahoo;

import com.kongpf8848.dmail.login.oauth.AuthorizationHeader;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface YahooApi {

    @GET("/openid/v1/userinfo")
    Single<YahooProfileResponse> profile(@Header("Authorization") AuthorizationHeader header);
}
