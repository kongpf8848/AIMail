package com.kongpf8848.dmail.login.oauth.hotmail;

import com.kongpf8848.dmail.login.oauth.DMAccountType;
import com.kongpf8848.dmail.login.oauth.OAuthConfiguration;

import java.util.Arrays;
import java.util.List;

public class HotmailOAuthConfiguration implements OAuthConfiguration {

    private static final String clientID = "e647013a-ada4-4114-b419-e43d250f99c5";
    private static final String clientSecret = null;
    private static final String authorizationURL = "https://login.live.com/oauth20_authorize.srf";
    private static final String tokenURL = "https://login.live.com/oauth20_token.srf";
    private static final String redirectURL = "msauth://com.fsck.k9.debug/VZF2DYuLYAu4TurFd6usQB2JPts%3D";
    private static final List<String> scopes = Arrays.asList("wl.basic", "wl.offline_access", "wl.emails", "wl.imap");

    @Override
    public DMAccountType getAccountType() {
        return DMAccountType.TYPE_HOTMAIL;
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
