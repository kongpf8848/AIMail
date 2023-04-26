package com.kongpf8848.dmail.oauth;

public enum OAuthConfig {

    OUTLOOK(OutlookProvider.identifier,OutlookProvider.clientID, null,OutlookProvider.authorizeURL,
            OutlookProvider.tokenURL, OutlookProvider.refreshTokenURL,
            OutlookProvider.redirectURL, OutlookProvider.scope);

    public String identifier;
    public String clientID;
    public String clientSecret;
    public String authorizeURL;
    public String tokenURL;
    public String refreshTokenURL;
    public String redirectURL;
    public String scope;

    OAuthConfig(String identifier,String clientID, String clientSecret,String authorizeURL, String tokenURL, String refreshTokenURL, String redirectURL, String scope) {
        this.identifier=identifier;
        this.clientID = clientID;
        this.clientSecret=clientSecret;
        this.authorizeURL = authorizeURL;
        this.tokenURL = tokenURL;
        this.refreshTokenURL = refreshTokenURL;
        this.redirectURL = redirectURL;
        this.scope = scope;
    }
}
