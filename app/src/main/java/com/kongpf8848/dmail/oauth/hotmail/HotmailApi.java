package com.kongpf8848.dmail.oauth.hotmail;

import com.kongpf8848.dmail.oauth.AuthorizationHeader;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface HotmailApi {

    @GET("/v5.0/me")
    Single<HotmailProfile> profile(@Header("Authorization") AuthorizationHeader header);
}
