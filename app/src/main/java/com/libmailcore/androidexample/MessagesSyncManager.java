package com.libmailcore.androidexample;

import com.libmailcore.ConnectionType;
import com.libmailcore.IMAPIdentity;
import com.libmailcore.IMAPMessage;
import com.libmailcore.IMAPSession;

public class MessagesSyncManager {
    public IMAPSession session;
    public IMAPMessage currentMessage;

    private static MessagesSyncManager theSingleton;

    public static MessagesSyncManager singleton() {
        if (theSingleton == null) {
            synchronized (MessagesSyncManager.class) {
                if (theSingleton == null) {
                    theSingleton = new MessagesSyncManager();
                }
            }
        }
        return theSingleton;
    }

    private MessagesSyncManager() {
        session = new IMAPSession();
        session.setUsername("xxx@163.com");
        //QQ,163邮箱,填写授权码，GMail，Outlook邮箱需要走OAuth2认证
        session.setPassword("xxxxxx");
        session.setHostname("imap.163.com");
        session.setPort(993);
        session.setConnectionType(ConnectionType.ConnectionTypeTLS);
        IMAPIdentity imapIdentity = session.clientIdentity();
        imapIdentity.setName("jack");
        imapIdentity.setVendor("jack");
        imapIdentity.setVersion("0.0.1");

    }


}
