package com.kongpf8848.dmail.oauth;

public interface GmailProvider {
    String identifier="gmail";
    String clientID = "262622259280-5qb3vtj68d5dtudmaif4g9vd3cpar8r3.apps.googleusercontent.com";
    String authorizeURL = "https://accounts.google.com/o/oauth2/v2/auth";
    String tokenURL = "https://oauth2.googleapis.com/token";
    String refreshTokenURL = "https://oauth2.googleapis.com/token";
    String redirectURL = "com.googleusercontent.apps.262622259280-5qb3vtj68d5dtudmaif4g9vd3cpar8r3:/oauth2redirect";
    String scope = "https://mail.google.com/ profile email openid";
}
