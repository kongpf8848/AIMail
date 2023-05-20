package com.kongpf8848.dmail.login.oauth;

public class AuthorizationHeader {

    private final String accessToken;

    public AuthorizationHeader(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "Bearer " + accessToken;
    }
}
