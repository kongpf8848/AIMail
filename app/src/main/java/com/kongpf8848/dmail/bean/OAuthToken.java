package com.kongpf8848.dmail.bean;

import java.io.Serializable;

public class OAuthToken implements Serializable {

    public String identifier;
    public String access_token;
    public String refresh_token;
    public Long expire_time;

}