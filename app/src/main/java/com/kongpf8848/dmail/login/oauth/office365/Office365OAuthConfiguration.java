package com.kongpf8848.dmail.login.oauth.office365;

import com.kongpf8848.dmail.login.DMAccountType;
import com.kongpf8848.dmail.login.oauth.OAuthConfiguration;

import java.util.Arrays;
import java.util.List;

public class Office365OAuthConfiguration implements OAuthConfiguration {

    private static final String clientID = "e647013a-ada4-4114-b419-e43d250f99c5";
    private static final String clientSecret = null;
    private static final String authorizationURL = "https://login.microsoftonline.com/common/oauth2/v2.0/authorize";
    private static final String tokenURL = "https://login.microsoftonline.com/common/oauth2/v2.0/token";
    private static final String redirectURL = "msauth://com.fsck.k9.debug/VZF2DYuLYAu4TurFd6usQB2JPts%3D";
    private static final List<String> scopes = Arrays.asList("https://outlook.office.com/IMAP.AccessAsUser.All","https://outlook.office.com/SMTP.Send","openid","email","profile","offline_access");

    @Override
    public DMAccountType getAccountType() {
        return DMAccountType.TYPE_OFFICE365;
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
