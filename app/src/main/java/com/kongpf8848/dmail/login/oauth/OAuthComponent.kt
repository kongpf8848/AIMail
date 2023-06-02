package com.kongpf8848.dmail.login.oauth

import com.kongpf8848.dmail.login.LoginActivity
import com.kongpf8848.dmail.login.oauth.hotmail.HotmailModule
import com.kongpf8848.dmail.login.oauth.yahoo.YahooModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [OAuthModule::class, HotmailModule::class, YahooModule::class])
interface OAuthComponent {
    fun inject(activity: LoginActivity)
}