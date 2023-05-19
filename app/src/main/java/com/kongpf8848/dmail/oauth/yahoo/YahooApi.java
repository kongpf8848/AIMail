package com.kongpf8848.dmail.oauth.yahoo;

import com.kongpf8848.dmail.oauth.AuthorizationHeader;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface YahooApi {

    @GET("/openid/v1/userinfo")
    Single<YahooProfileResponse> profile(@Header("Authorization") AuthorizationHeader header);
}
