package com.kongpf8848.dmail.oauth;

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
