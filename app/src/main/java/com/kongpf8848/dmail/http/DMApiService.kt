package com.kongpf8848.dmail.http

import com.kongpf8848.dmail.bean.server.resp.OutlookUserInfoResp
import com.kongpf8848.dmail.bean.server.resp.YahooUserInfoResp
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object DMApiService {

    @JvmStatic
    fun getUserInfoForOutlook(access_token: String): Observable<OutlookUserInfoResp> {
        return RetrofitManager.instance.apiInterface!!.getUserInfoForOutlook(access_token)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    @JvmStatic
    fun getUserInfoForYahoo(access_token: String): Observable<YahooUserInfoResp> {
        return RetrofitManager.instance.apiInterface!!.getUserInfoForYahoo("Bearer $access_token")
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

}