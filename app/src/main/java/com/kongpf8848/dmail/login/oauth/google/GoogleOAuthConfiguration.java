package com.kongpf8848.dmail.login.oauth.google;

import com.kongpf8848.dmail.login.DMAccountType;
import com.kongpf8848.dmail.login.oauth.OAuthConfiguration;

import java.util.Arrays;
import java.util.List;

public class GoogleOAuthConfiguration implements OAuthConfiguration {

    private static final String clientID = "262622259280-5qb3vtj68d5dtudmaif4g9vd3cpar8r3.apps.googleusercontent.com";
    private static final String clientSecret = null;
    private static final String authorizationURL = "https://accounts.google.com/o/oauth2/v2/auth";
    private static final String tokenURL = "https://oauth2.googleapis.com/token";
    private static final String redirectURL = "com.googleusercontent.apps.262622259280-5qb3vtj68d5dtudmaif4g9vd3cpar8r3:/oauth2redirect";
    private static final List<String> scopes = Arrays.asList("https://mail.google.com/", "openid", "profile", "email");


    @Override
    public DMAccountType getAccountType() {
        return DMAccountType.TYPE_GOOGLE;
    }

    @Override
    public String getClientId() {
        return clientID;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    @Override
    public String getAuthorizationURL() {
        return authorizationURL;
    }

    @Override
    public String getTokenURL() {
        return tokenURL;
    }

    @Override
    public String getRedirectURL() {
        return redirectURL;
    }

    @Override
    public List<String> getScopes() {
        return scopes;
    }
}
