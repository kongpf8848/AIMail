package com.kongpf8848.dmail.oauth.yahoo;

import com.kongpf8848.dmail.oauth.DMAccountType;
import com.kongpf8848.dmail.oauth.OAuthConfiguration;

import java.util.Arrays;
import java.util.List;

public class YahooOAuthConfiguration implements OAuthConfiguration {

    private static final String clientID = "dj0yJmk9ZVN2ZWQwSGxhNVhhJmQ9WVdrOU9VNXJlVmRoY1hJbWNHbzlNQT09JnM9Y29uc3VtZXJzZWNyZXQmc3Y9MCZ4PTAz";
    private static final String clientSecret="01484dfed7cece1de1d15308dccc35d2cf140083";
    private static final String authorizationURL = "https://api.login.yahoo.com/oauth2/request_auth";
    private static final String tokenURL = "https://api.login.yahoo.com/oauth2/get_token";
    private static final String redirectURL = "com.kongpf8848.dmail://oauth2redirect";
    private static final List<String> scopes = Arrays.asList("openid");


    @Override
    public DMAccountType getAccountType() {
        return DMAccountType.TYPE_YAHOO;
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
