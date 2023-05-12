package com.kongpf8848.dmail.oauth;

public interface YahooProvider {
    String identifier="yahoo";
    String clientID = "dj0yJmk9ZVN2ZWQwSGxhNVhhJmQ9WVdrOU9VNXJlVmRoY1hJbWNHbzlNQT09JnM9Y29uc3VtZXJzZWNyZXQmc3Y9MCZ4PTAz";
    String clientSecret="01484dfed7cece1de1d15308dccc35d2cf140083";
    String authorizeURL = "https://api.login.yahoo.com/oauth2/request_auth";
    String tokenURL = "https://api.login.yahoo.com/oauth2/get_token";
    String refreshTokenURL = "https://api.login.yahoo.com/oauth2/get_token";
    String redirectURL = "com.kongpf8848.dmail://oauth2redirect";
    String scope = "openid";
}
