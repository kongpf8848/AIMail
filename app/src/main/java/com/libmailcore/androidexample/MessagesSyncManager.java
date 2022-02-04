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
        //username，password替换成自己的信息
        String username="xxx@163.com";
        String password="xxxxxx";
        String hostname="imap.163.com";
        session = new IMAPSession();
        session.setUsername(username);
        session.setPassword(password);
        session.setHostname(hostname);
        session.setPort(993);
        session.setConnectionType(ConnectionType.ConnectionTypeTLS);
        IMAPIdentity imapIdentity = session.clientIdentity();
        imapIdentity.setName("jack");
        imapIdentity.setVendor("jack");
        imapIdentity.setVersion("0.0.1");
    }


}
