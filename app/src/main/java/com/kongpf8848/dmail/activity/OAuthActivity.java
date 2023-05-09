package com.kongpf8848.dmail.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import com.kongpf8848.dmail.bean.OAuthToken;
import com.kongpf8848.dmail.http.DMApiService;
import com.kongpf8848.dmail.oauth.OAuthConfig;
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

public abstract class OAuthActivity extends BaseActivity {

    public static final int RC_OAUTH = 4099;

    private AuthorizationService authService;
    private OAuthConfig config;
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

    protected void doAuth(OAuthConfig config, String address) {
        this.config = config;
        this.address = address;

        AuthorizationServiceConfiguration serviceConfig = new AuthorizationServiceConfiguration(
                Uri.parse(config.authorizeURL),
                Uri.parse(config.tokenURL));
        AuthorizationRequest.Builder authRequestBuilder =
                new AuthorizationRequest.Builder(
                        serviceConfig,
                        config.clientID,
                        ResponseTypeValues.CODE,
                        Uri.parse(config.redirectURL));
        if (!TextUtils.isEmpty(this.address)) {
            authRequestBuilder.setLoginHint(this.address);
        }
        AuthorizationRequest authRequest = authRequestBuilder.setScope(config.scope).build();
        Intent authIntent = authService.getAuthorizationRequestIntent(authRequest);
        startActivityForResult(authIntent, RC_OAUTH);

    }

    private void getAuthorizationCodeSuccess(AuthorizationResponse r) {
        ClientAuthentication authentication = null;
        if (!TextUtils.isEmpty(config.clientSecret)) {
            authentication = new ClientSecretPost(config.clientSecret);
        } else {
            authentication = NoClientAuthentication.INSTANCE;
        }
        authService.performTokenRequest(r.createTokenExchangeRequest(), authentication, (resp, ex) -> {
            Log.d(TAG, "getAuthorizationCodeSuccess() called with: resp = [" + resp + "]");
            if (resp != null) {
                if(config.isOutlook()) {
                    getUserInfoForOutlook(resp);
                }else if(config.isGmail()){
                    this.address=TokenUtils.getEmailFromIdToken(resp.idToken);
                    OAuthToken token = new OAuthToken();
                    token.identifier = config.identifier;
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

    private void getUserInfoForOutlook( TokenResponse resp){
        DMApiService.INSTANCE.getUserInfoForOutlook(resp.accessToken).subscribe(outlookUserInfoResp -> {
            this.address=outlookUserInfoResp.emails.account;
            OAuthToken token = new OAuthToken();
            token.identifier = config.identifier;
            token.access_token = resp.accessToken;
            token.refresh_token = resp.refreshToken;
            token.expire_time = resp.accessTokenExpirationTime;
            onOAuthTokenSuccess(this.address, token);
        }, throwable -> {
            throwable.printStackTrace();
        });
    }

}
