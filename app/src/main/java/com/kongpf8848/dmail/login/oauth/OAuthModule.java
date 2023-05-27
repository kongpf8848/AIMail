package com.kongpf8848.dmail.login.oauth;

import com.kongpf8848.dmail.login.oauth.google.GoogleOAuthConfiguration;
import com.kongpf8848.dmail.login.oauth.hotmail.HotmailOAuthConfiguration;
import com.kongpf8848.dmail.login.oauth.qualifiers.Google;
import com.kongpf8848.dmail.login.oauth.qualifiers.Hotmail;
import com.kongpf8848.dmail.login.oauth.qualifiers.Yahoo;
import com.kongpf8848.dmail.login.oauth.yahoo.YahooOAuthConfiguration;

import dagger.Module;
import dagger.Provides;

@Module
public class OAuthModule {

    @Google
    @Provides
    public OAuthConfiguration provideGoogleConfiguration() {
        return new GoogleOAuthConfiguration();
    }

    @Hotmail
    @Provides
    public OAuthConfiguration provideHotmailConfiguration() {
        return new HotmailOAuthConfiguration();
    }

    @Yahoo
    @Provides
    public OAuthConfiguration provideYahooConfiguration() {
        return new YahooOAuthConfiguration();
    }

}
