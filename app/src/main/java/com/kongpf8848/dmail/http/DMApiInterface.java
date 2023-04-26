package com.kongpf8848.dmail.http;

import com.kongpf8848.dmail.bean.server.resp.OutlookUserInfoResp;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface DMApiInterface {

    @GET
    Observable<OutlookUserInfoResp> getUserInfoForOutlook(@Url String url, @Query("access_token") String access_token);

}
