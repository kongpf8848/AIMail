package com.kongpf8848.dmail.oauth;

public interface OutlookProvider {
    String identifier="outlook";
    String clientID = "e647013a-ada4-4114-b419-e43d250f99c5";
    String clientSecret=null;
    String authorizeURL = "https://login.live.com/oauth20_authorize.srf";
    String tokenURL = "https://login.live.com/oauth20_token.srf";
    String refreshTokenURL = "https://login.live.com/oauth20_token.srf";
    String redirectURL = "msauth://com.fsck.k9.debug/VZF2DYuLYAu4TurFd6usQB2JPts%3D";
    String scope = "wl.basic wl.offline_access wl.emails wl.imap";
}
