package com.kongpf8848.dmail.http

import com.kongpf8848.dmail.bean.server.resp.OutlookUserInfoResp
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object DMApiService {

    @JvmStatic
    fun getUserInfoForOutlook(access_token: String): Observable<OutlookUserInfoResp> {
        return RetrofitManager.instance.apiInterface!!.getUserInfoForOutlook(
            HttpConstants.URL_OUTLOOK_USER_INFO,
            access_token
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

}