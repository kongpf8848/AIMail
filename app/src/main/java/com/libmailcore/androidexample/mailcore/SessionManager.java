package com.libmailcore.androidexample.mailcore;

import com.libmailcore.AuthType;
import com.libmailcore.ConnectionType;
import com.libmailcore.IMAPIdentity;
import com.libmailcore.IMAPSession;
import com.libmailcore.SMTPSession;
import com.libmailcore.androidexample.bean.OAuthToken;

import java.util.Date;

public class SessionManager {

    public static IMAPSession buildImapSession(String userName, String password, String host, int port, int connectionType, OAuthToken token) {
        IMAPSession imapSession = new IMAPSessionWithToken(token);
        imapSession.setUsername(userName);
        imapSession.setPassword(password);
        imapSession.setHostname(host);
        imapSession.setPort(port);
        imapSession.setConnectionType(connectionType);
        imapSession.setTimeout(15);
        imapSession.setMaximumConnections(15);
        imapSession.setCheckCertificateEnabled(false);

        if (token != null) {
            imapSession.setOAuth2Token(token.access_token);
            switch (token.identifier) {
                case "Outlook":
                    imapSession.setAuthType(AuthType.AuthTypeXOAuth2Outlook);
                    break;
                default:
                    imapSession.setAuthType(AuthType.AuthTypeXOAuth2);
                    break;
            }
        }

        IMAPIdentity imapIdentity = imapSession.clientIdentity();
        imapIdentity.setVendor("jack");
        imapIdentity.setName("jack");
        imapIdentity.setVersion("0.0.1");

        return imapSession;
    }

    public static SMTPSession buildSmtpSession(String userName, String password, String host, int port, int connectionType, OAuthToken token) {
        SMTPSession smtpSession = new SMTPSessionWithToken(token);
        smtpSession.setUsername(userName);
        smtpSession.setPassword(password);
        smtpSession.setHostname(host);
        smtpSession.setPort(port);
        smtpSession.setConnectionType(connectionType);
        smtpSession.setTimeout(15);
        smtpSession.setCheckCertificateEnabled(false);

        if (token != null) {
            smtpSession.setOAuth2Token(token.access_token);
            switch (token.identifier) {
                case "Outlook":
                    smtpSession.setAuthType(AuthType.AuthTypeXOAuth2Outlook);
                    break;
                default:
                    smtpSession.setAuthType(AuthType.AuthTypeXOAuth2);
                    break;
            }
        }

        return smtpSession;
    }

}
