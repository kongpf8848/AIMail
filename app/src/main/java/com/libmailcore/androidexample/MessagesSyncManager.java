package com.libmailcore.androidexample;

import com.libmailcore.ConnectionType;
import com.libmailcore.IMAPIdentity;
import com.libmailcore.IMAPMessage;
import com.libmailcore.IMAPSession;
import com.libmailcore.SMTPSession;

public class MessagesSyncManager {
    public IMAPSession imapSession;
    public SMTPSession smtpSession;
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
        imapSession = new IMAPSession();
        imapSession.setUsername(username);
        imapSession.setPassword(password);
        imapSession.setHostname(hostname);
        imapSession.setPort(993);
        imapSession.setConnectionType(ConnectionType.ConnectionTypeTLS);
        imapSession.setTimeout(15);
        imapSession.setCheckCertificateEnabled(false);
        IMAPIdentity imapIdentity = imapSession.clientIdentity();
        imapIdentity.setName("jack");
        imapIdentity.setVendor("jack");
        imapIdentity.setVersion("0.0.1");

        smtpSession = new SMTPSession();
        smtpSession.setUsername("xxx@163.com");
        smtpSession.setPassword(password);
        smtpSession.setHostname("smtp.163.com");
        smtpSession.setPort(465);
        smtpSession.setConnectionType(ConnectionType.ConnectionTypeTLS);
        smtpSession.setTimeout(15);
        //smtpSession.setCheckCertificateEnabled(false);
    }


}
