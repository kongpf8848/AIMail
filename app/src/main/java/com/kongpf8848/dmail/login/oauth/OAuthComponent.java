package com.kongpf8848.dmail.login.oauth;

import com.kongpf8848.dmail.login.LoginActivity;
import com.kongpf8848.dmail.login.oauth.hotmail.HotmailModule;

import dagger.Component;

@Component(modules = {OAuthModule.class, HotmailModule.class})
public interface OAuthComponent {
    void inject(LoginActivity activity);
}