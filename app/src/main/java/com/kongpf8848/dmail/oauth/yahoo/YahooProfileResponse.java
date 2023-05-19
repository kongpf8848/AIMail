package com.kongpf8848.dmail.oauth.yahoo;

/**
 * {
 * "sub": "AN4FL4S53NXH6QJHJRVMAM2PLE",
 * "name": "xxx",
 * "given_name": "xxx",
 * "family_name": "xxx",
 * "locale": "zh-CN",
 * "email": "xxx@yahoo.com",
 * "email_verified": true,
 * "profile_images": {
 * "image32": "xxx.jpg",
 * "image64": "xxx.jpg",
 * "image128": "xxx.jpg",
 * "image192": "xxx.jpg"
 * },
 * "nickname": "xxx",
 * "picture": "xxx.jpg"
 * }
 */
public class YahooProfileResponse {

    private String sub;
    private String name;
    private String given_name;
    private String family_name;
    private String locale;
    private String email;
    private boolean email_verified;
    private String nickname;
    private String picture;

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGiven_name() {
        return given_name;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmail_verified() {
        return email_verified;
    }

    public void setEmail_verified(boolean email_verified) {
        this.email_verified = email_verified;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
