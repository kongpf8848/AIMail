package com.libmailcore.androidexample.mailcore;

import com.libmailcore.SMTPSession;
import com.libmailcore.androidexample.bean.OAuthToken;

public class SMTPSessionWithToken extends SMTPSession {

    private OAuthToken token;

    public SMTPSessionWithToken(final OAuthToken token) {
       this.token=token;
    }

    public OAuthToken getToken() {
       return token;
    }

}
