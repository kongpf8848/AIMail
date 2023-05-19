package com.kongpf8848.dmail.oauth;

import com.kongpf8848.dmail.oauth.google.GoogleOAuthConfiguration;
import com.kongpf8848.dmail.oauth.hotmail.HotmailOAuthConfiguration;
import com.kongpf8848.dmail.oauth.yahoo.YahooOAuthConfiguration;

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
