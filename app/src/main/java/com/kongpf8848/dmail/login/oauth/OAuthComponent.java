package com.kongpf8848.dmail.login.oauth;

import com.kongpf8848.dmail.login.LoginActivity;

import dagger.Component;

@Component(modules = {OAuthModule.class})
public interface OAuthComponent {
    void inject(LoginActivity activity);
}