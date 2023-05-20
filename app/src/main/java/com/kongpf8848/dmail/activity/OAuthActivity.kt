package com.kongpf8848.dmail.activity

import com.kongpf8848.dmail.util.TokenUtils.getEmailFromIdToken
import com.kongpf8848.dmail.activity.BaseActivity
import com.kongpf8848.dmail.login.oauth.OAuthConfiguration
import com.kongpf8848.dmail.bean.OAuthToken
import android.os.Bundle
import android.content.Intent
import android.app.Activity
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import com.kongpf8848.dmail.activity.OAuthActivity
import net.openid.appauth.AuthorizationService.TokenResponseCallback
import com.kongpf8848.dmail.login.oauth.DMAccountType
import com.kongpf8848.dmail.util.TokenUtils
import com.kongpf8848.dmail.login.oauth.hotmail.HotmailModule
import com.kongpf8848.dmail.login.oauth.AuthorizationHeader
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import com.kongpf8848.dmail.login.oauth.hotmail.HotmailProfile
import com.kongpf8848.dmail.login.oauth.yahoo.YahooModule
import com.kongpf8848.dmail.login.oauth.yahoo.YahooProfileResponse
import net.openid.appauth.*

abstract class OAuthActivity : BaseActivity() {

    private lateinit var authService: AuthorizationService
    private var config: OAuthConfiguration? = null
    private var address: String? = null
    abstract fun onOAuthTokenSuccess(address: String?, token: OAuthToken?)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authService = AuthorizationService(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) {
            return
        }
        if (requestCode== RC_OAUTH) {
            val resp = AuthorizationResponse.fromIntent(data!!)
            val ex = AuthorizationException.fromIntent(data)
            resp?.let { getAuthorizationCodeSuccess(it) } ?: ex?.printStackTrace()
        }
    }

    protected fun doAuth(config: OAuthConfiguration, address: String?) {
        this.config = config
        this.address = address
        val serviceConfig = AuthorizationServiceConfiguration(
            Uri.parse(config.authorizationURL),
            Uri.parse(config.tokenURL)
        )
        val authRequestBuilder = AuthorizationRequest.Builder(
            serviceConfig,
            config.clientId,
            ResponseTypeValues.CODE,
            Uri.parse(config.redirectURL)
        )
        if (!TextUtils.isEmpty(this.address)) {
            authRequestBuilder.setLoginHint(this.address)
        }
        val authRequest = authRequestBuilder
            .setScope(TextUtils.join(",", config.scopes)).build()
        val authIntent = authService!!.getAuthorizationRequestIntent(authRequest)
        startActivityForResult(authIntent, RC_OAUTH)
    }

    private fun getAuthorizationCodeSuccess(r: AuthorizationResponse) {
        var authentication: ClientAuthentication? = null
        authentication = if (!TextUtils.isEmpty(config!!.clientSecret)) {
            ClientSecretPost(config!!.clientSecret)
        } else {
            NoClientAuthentication.INSTANCE
        }
        authService!!.performTokenRequest(
            r.createTokenExchangeRequest(),
            authentication!!
        ) { resp: TokenResponse?, ex: AuthorizationException? ->
            if (resp != null) {
                Log.d(
                    TAG,
                    "getAuthorizationCodeSuccess() called with: resp = [" + resp.jsonSerializeString() + "]"
                )
                if (config!!.accountType == DMAccountType.TYPE_HOTMAIL) {
                    getUserInfoForOutlook(resp)
                } else if (config!!.accountType == DMAccountType.TYPE_YAHOO) {
                    address = getEmailFromIdToken(resp.idToken!!)
                    if (TextUtils.isEmpty(address)) {
                        getUserInfoForYahoo(resp)
                    } else {
                        val token = OAuthToken()
                        token.accountType = DMAccountType.TYPE_YAHOO
                        token.access_token = resp.accessToken
                        token.refresh_token = resp.refreshToken
                        token.expire_time = resp.accessTokenExpirationTime
                        onOAuthTokenSuccess(address, token)
                    }
                } else if (config!!.accountType == DMAccountType.TYPE_GOOGLE) {
                    address = getEmailFromIdToken(resp.idToken!!)
                    val token = OAuthToken()
                    token.accountType = DMAccountType.TYPE_GOOGLE
                    token.access_token = resp.accessToken
                    token.refresh_token = resp.refreshToken
                    token.expire_time = resp.accessTokenExpirationTime
                    onOAuthTokenSuccess(address, token)
                }
            } else {
                Log.e(TAG, "getAuthorizationCodeSuccess() called with: ex = [$ex]")
            }
        }
    }

    private fun getUserInfoForOutlook(resp: TokenResponse) {
        HotmailModule.provideHotmailApi().profile(AuthorizationHeader(resp.accessToken))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { profile: HotmailProfile?, throwable: Throwable? ->
                if (profile != null) {
                    address = profile.emails.account
                    val token = OAuthToken()
                    token.accountType = DMAccountType.TYPE_HOTMAIL
                    token.access_token = resp.accessToken
                    token.refresh_token = resp.refreshToken
                    token.expire_time = resp.accessTokenExpirationTime
                    onOAuthTokenSuccess(address, token)
                }
            }
    }

    private fun getUserInfoForYahoo(resp: TokenResponse) {
        YahooModule.provideYahooApi().profile(AuthorizationHeader(resp.accessToken))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { profile: YahooProfileResponse?, throwable: Throwable? ->
                if (profile != null) {
                    address = profile.email
                    val token = OAuthToken()
                    token.accountType = DMAccountType.TYPE_YAHOO
                    token.access_token = resp.accessToken
                    token.refresh_token = resp.refreshToken
                    token.expire_time = resp.accessTokenExpirationTime
                    onOAuthTokenSuccess(address, token)
                }
            }
    }

    companion object {
        const val RC_OAUTH = 4099
    }
}