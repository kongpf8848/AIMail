package com.kongpf8848.dmail.mailcore

import com.kongpf8848.dmail.bean.OAuthToken
import com.libmailcore.IMAPSession
import com.kongpf8848.dmail.mailcore.IMAPSessionWithToken
import com.kongpf8848.dmail.login.oauth.DMAccountType
import com.libmailcore.AuthType
import com.libmailcore.IMAPIdentity
import com.libmailcore.SMTPSession
import com.kongpf8848.dmail.mailcore.SMTPSessionWithToken

object SessionManager {
    @JvmStatic
    fun buildImapSession(
        userName: String?,
        password: String?,
        host: String?,
        port: Int,
        connectionType: Int,
        token: OAuthToken?
    ): IMAPSession {
        val imapSession: IMAPSession = IMAPSessionWithToken(token)
        imapSession.setUsername(userName)
        imapSession.setPassword(password)
        imapSession.setHostname(host)
        imapSession.setPort(port)
        imapSession.setConnectionType(connectionType)
        imapSession.setTimeout(15)
        imapSession.setMaximumConnections(15)
        imapSession.isCheckCertificateEnabled = false
        if (token != null) {
            imapSession.setOAuth2Token(token.access_token)
            when (token.accountType) {
                DMAccountType.TYPE_HOTMAIL -> imapSession.setAuthType(AuthType.AuthTypeXOAuth2Outlook)
                else -> imapSession.setAuthType(AuthType.AuthTypeXOAuth2)
            }
        }
        val imapIdentity = imapSession.clientIdentity()
        imapIdentity.setVendor("jack")
        imapIdentity.setName("jack")
        imapIdentity.setVersion("0.0.1")
        return imapSession
    }

    fun buildSmtpSession(
        userName: String?,
        password: String?,
        host: String?,
        port: Int,
        connectionType: Int,
        token: OAuthToken
    ): SMTPSession {
        val smtpSession: SMTPSession = SMTPSessionWithToken(token)
        smtpSession.setUsername(userName)
        smtpSession.setPassword(password)
        smtpSession.setHostname(host)
        smtpSession.setPort(port)
        smtpSession.setConnectionType(connectionType)
        smtpSession.setTimeout(15)
        smtpSession.isCheckCertificateEnabled = false
        if (token != null) {
            smtpSession.setOAuth2Token(token.access_token)
            when (token.accountType) {
                DMAccountType.TYPE_HOTMAIL -> smtpSession.setAuthType(AuthType.AuthTypeXOAuth2Outlook)
                else -> smtpSession.setAuthType(AuthType.AuthTypeXOAuth2)
            }
        }
        return smtpSession
    }
}