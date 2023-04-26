package com.libmailcore.androidexample.mailcore;

import com.libmailcore.IMAPSession;
import com.libmailcore.androidexample.bean.OAuthToken;

public class IMAPSessionWithToken extends IMAPSession {
    private OAuthToken token;

    public IMAPSessionWithToken(final OAuthToken token) {
        this.token=token;
    }

    public  OAuthToken getToken() {
        return token;
    }

}
