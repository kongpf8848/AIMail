package com.kongpf8848.dmail.http;

import com.kongpf8848.dmail.bean.server.resp.OutlookUserInfoResp;
import com.kongpf8848.dmail.bean.server.resp.YahooUserInfoResp;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface DMApiInterface {

    @GET(HttpConstants.URL_OUTLOOK_USER_INFO)
    Observable<OutlookUserInfoResp> getUserInfoForOutlook(
            @Query("access_token") String access_token
    );

    @GET(HttpConstants.URL_YAHOO_USER_INFO)
    Observable<YahooUserInfoResp> getUserInfoForYahoo(
            @Header("Authorization") String authorization
    );
}
