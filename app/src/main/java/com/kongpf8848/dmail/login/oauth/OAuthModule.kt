package com.kongpf8848.dmail.login.oauth

import com.kongpf8848.dmail.login.oauth.google.GoogleOAuthConfiguration
import com.kongpf8848.dmail.login.oauth.hotmail.HotmailOAuthConfiguration
import com.kongpf8848.dmail.login.oauth.office365.Office365OAuthConfiguration
import com.kongpf8848.dmail.login.oauth.yahoo.YahooOAuthConfiguration
import com.kongpf8848.dmail.login.oauth.qualifiers.Google
import com.kongpf8848.dmail.login.oauth.qualifiers.Hotmail
import com.kongpf8848.dmail.login.oauth.qualifiers.Office365
import com.kongpf8848.dmail.login.oauth.qualifiers.Yahoo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class OAuthModule {

    @Singleton
    @Google
    @Provides
    fun provideGoogleConfiguration(): OAuthConfiguration {
        return GoogleOAuthConfiguration()
    }

    @Singleton
    @Hotmail
    @Provides
    fun provideHotmailConfiguration(): OAuthConfiguration {
        return HotmailOAuthConfiguration()
    }

    @Singleton
    @Office365
    @Provides
    fun provideOffice365Configuration(): OAuthConfiguration {
        return Office365OAuthConfiguration()
    }

    @Singleton
    @Yahoo
    @Provides
    fun provideYahooConfiguration(): OAuthConfiguration {
        return YahooOAuthConfiguration()
    }
}