package com.kongpf8848.dmail.login.oauth;

import com.kongpf8848.dmail.login.DMAccountType;

import java.util.List;

public interface OAuthConfiguration {

    DMAccountType getAccountType();

    String getClientId();

    String getClientSecret();

    String getAuthorizationURL();

    String getTokenURL();

    String getRedirectURL();

    List<String> getScopes();

}
