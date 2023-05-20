package com.kongpf8848.dmail.mailcore;

import com.libmailcore.IMAPSession;
import com.kongpf8848.dmail.bean.OAuthToken;

public class IMAPSessionWithToken extends IMAPSession {
    private OAuthToken token;

    public IMAPSessionWithToken(final OAuthToken token) {
        this.token=token;
    }

    public  OAuthToken getToken() {
        return token;
    }

}
