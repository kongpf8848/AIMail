package com.kongpf8848.dmail.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import com.kongpf8848.dmail.bean.OAuthToken;
import com.kongpf8848.dmail.login.oauth.AuthorizationHeader;
import com.kongpf8848.dmail.login.oauth.DMAccountType;
import com.kongpf8848.dmail.login.oauth.OAuthConfiguration;
import com.kongpf8848.dmail.login.oauth.hotmail.HotmailModule;
import com.kongpf8848.dmail.login.oauth.yahoo.YahooModule;
import com.kongpf8848.dmail.util.TokenUtils;

import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationRequest;
import net.openid.appauth.AuthorizationResponse;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.AuthorizationServiceConfiguration;
import net.openid.appauth.ClientAuthentication;
import net.openid.appauth.ClientSecretPost;
import net.openid.appauth.NoClientAuthentication;
import net.openid.appauth.ResponseTypeValues;
import net.openid.appauth.TokenResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public abstract class OAuthActivity extends BaseActivity {

    public static final int RC_OAUTH = 4099;

    private AuthorizationService authService;
    private OAuthConfiguration config;
    private String address;

    public abstract void onOAuthTokenSuccess(String address, OAuthToken token);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authService = new AuthorizationService(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (RC_OAUTH == requestCode) {
            AuthorizationResponse resp = AuthorizationResponse.fromIntent(data);
            AuthorizationException ex = AuthorizationException.fromIntent(data);
            if (resp != null) {
                getAuthorizationCodeSuccess(resp);
            } else if (ex != null) {
                ex.printStackTrace();
            }
        }
    }

    protected void doAuth(OAuthConfiguration config, String address) {
        this.config = config;
        this.address = address;

        AuthorizationServiceConfiguration serviceConfig = new AuthorizationServiceConfiguration(
                Uri.parse(config.getAuthorizationURL()),
                Uri.parse(config.getTokenURL()));
        AuthorizationRequest.Builder authRequestBuilder =
                new AuthorizationRequest.Builder(
                        serviceConfig,
                        config.getClientId(),
                        ResponseTypeValues.CODE,
                        Uri.parse(config.getRedirectURL()));
        if (!TextUtils.isEmpty(this.address)) {
            authRequestBuilder.setLoginHint(this.address);
        }
        AuthorizationRequest authRequest = authRequestBuilder
                .setScope(TextUtils.join(",", config.getScopes())).build();
        Intent authIntent = authService.getAuthorizationRequestIntent(authRequest);
        startActivityForResult(authIntent, RC_OAUTH);

    }

    private void getAuthorizationCodeSuccess(AuthorizationResponse r) {
        ClientAuthentication authentication = null;
        if (!TextUtils.isEmpty(config.getClientSecret())) {
            authentication = new ClientSecretPost(config.getClientSecret());
        } else {
            authentication = NoClientAuthentication.INSTANCE;
        }
        authService.performTokenRequest(r.createTokenExchangeRequest(), authentication, (resp, ex) -> {
            if (resp != null) {
                Log.d(TAG, "getAuthorizationCodeSuccess() called with: resp = [" + resp.jsonSerializeString() + "]");
                if (config.getAccountType() == DMAccountType.TYPE_HOTMAIL) {
                    getUserInfoForOutlook(resp);
                } else if (config.getAccountType() == DMAccountType.TYPE_YAHOO) {
                    this.address = TokenUtils.getEmailFromIdToken(resp.idToken);
                    if (TextUtils.isEmpty(this.address)) {
                        getUserInfoForYahoo(resp);
                    }else {
                        OAuthToken token = new OAuthToken();
                        token.accountType = DMAccountType.TYPE_YAHOO;
                        token.access_token = resp.accessToken;
                        token.refresh_token = resp.refreshToken;
                        token.expire_time = resp.accessTokenExpirationTime;
                        onOAuthTokenSuccess(this.address, token);
                    }
                } else if (config.getAccountType() == DMAccountType.TYPE_GOOGLE) {
                    this.address = TokenUtils.getEmailFromIdToken(resp.idToken);
                    OAuthToken token = new OAuthToken();
                    token.accountType = DMAccountType.TYPE_GOOGLE;
                    token.access_token = resp.accessToken;
                    token.refresh_token = resp.refreshToken;
                    token.expire_time = resp.accessTokenExpirationTime;
                    onOAuthTokenSuccess(this.address, token);
                }
            } else {
                Log.e(TAG, "getAuthorizationCodeSuccess() called with: ex = [" + ex + "]");
            }
        });
    }

    private void getUserInfoForOutlook(TokenResponse resp) {
        HotmailModule.provideHotmailApi().profile(new AuthorizationHeader(resp.accessToken))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((profile, throwable) -> {
                    if (profile != null) {
                        OAuthActivity.this.address = profile.getEmails().getAccount();
                        OAuthToken token = new OAuthToken();
                        token.accountType = DMAccountType.TYPE_HOTMAIL;
                        token.access_token = resp.accessToken;
                        token.refresh_token = resp.refreshToken;
                        token.expire_time = resp.accessTokenExpirationTime;
                        onOAuthTokenSuccess(OAuthActivity.this.address, token);
                    }
                });

    }

    private void getUserInfoForYahoo(TokenResponse resp) {
        YahooModule.provideYahooApi().profile(new AuthorizationHeader(resp.accessToken))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((profile, throwable) -> {
                    if (profile != null) {
                        this.address = profile.getEmail();
                        OAuthToken token = new OAuthToken();
                        token.accountType = DMAccountType.TYPE_YAHOO;
                        token.access_token = resp.accessToken;
                        token.refresh_token = resp.refreshToken;
                        token.expire_time = resp.accessTokenExpirationTime;
                        onOAuthTokenSuccess(this.address, token);
                    }
                });
    }

}
