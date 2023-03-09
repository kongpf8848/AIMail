package com.libmailcore.androidexample.api;

import com.libmailcore.ConnectionType;
import com.libmailcore.IMAPIdentity;
import com.libmailcore.IMAPSession;

public class SessionManager {

    public static IMAPSession getIMAPSession(){
        //username，password替换成自己的信息
        String username = "xxxxx@tom.com";
        String password = "xxxx";
        String hostname = "imap.tom.com";
        IMAPSession imapSession = new IMAPSession();
        imapSession.setUsername(username);
        imapSession.setPassword(password);
        imapSession.setHostname(hostname);
        imapSession.setPort(143);
        imapSession.setConnectionType(ConnectionType.ConnectionTypeClear);
        imapSession.setTimeout(15);
        imapSession.setCheckCertificateEnabled(false);
        IMAPIdentity imapIdentity = imapSession.clientIdentity();
        imapIdentity.setName("jack");
        imapIdentity.setVendor("jack");
        imapIdentity.setVersion("0.0.1");
        return imapSession;
    }


}
