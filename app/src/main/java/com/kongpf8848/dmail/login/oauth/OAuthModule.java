package com.kongpf8848.dmail.login.oauth;

import com.kongpf8848.dmail.login.oauth.google.GoogleOAuthConfiguration;
import com.kongpf8848.dmail.login.oauth.hotmail.HotmailOAuthConfiguration;
import com.kongpf8848.dmail.login.oauth.yahoo.YahooOAuthConfiguration;

public class OAuthModule {

    public static OAuthConfiguration provideGoogleConfiguration() {
        return new GoogleOAuthConfiguration();
    }

    public static OAuthConfiguration provideHotmailConfiguration() {
        return new HotmailOAuthConfiguration();
    }

    public static OAuthConfiguration provideYahooConfiguration() {
        return new YahooOAuthConfiguration();
    }

}
