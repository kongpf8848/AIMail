package com.kongpf8848.dmail.login.oauth;

import com.kongpf8848.dmail.login.LoginActivity;
import com.kongpf8848.dmail.login.oauth.hotmail.HotmailModule;
import com.kongpf8848.dmail.login.oauth.yahoo.YahooModule;

import dagger.Component;

@Component(modules = {OAuthModule.class, HotmailModule.class, YahooModule.class})
public interface OAuthComponent {
    void inject(LoginActivity activity);
}