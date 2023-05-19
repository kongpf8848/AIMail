package com.kongpf8848.dmail.bean;

import com.kongpf8848.dmail.oauth.DMAccountType;

import java.io.Serializable;

public class OAuthToken implements Serializable {
    public DMAccountType accountType;
    public String access_token;
    public String refresh_token;
    public Long expire_time;

}
