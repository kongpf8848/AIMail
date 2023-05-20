package com.kongpf8848.dmail.mailcore;

import com.libmailcore.SMTPSession;
import com.kongpf8848.dmail.bean.OAuthToken;

public class SMTPSessionWithToken extends SMTPSession {

    private OAuthToken token;

    public SMTPSessionWithToken(final OAuthToken token) {
       this.token=token;
    }

    public OAuthToken getToken() {
       return token;
    }

}
