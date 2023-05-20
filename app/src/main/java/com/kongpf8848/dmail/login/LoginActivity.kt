package com.kongpf8848.dmail.login

import android.content.Intent
import android.os.Bundle
import com.kongpf8848.dmail.R
import com.kongpf8848.dmail.activity.IMAPLoginActivity
import com.kongpf8848.dmail.activity.MessageViewListActivity
import com.kongpf8848.dmail.activity.OAuthActivity
import com.kongpf8848.dmail.bean.MailConfig
import com.kongpf8848.dmail.bean.OAuthToken
import com.kongpf8848.dmail.login.oauth.OAuthModule
import com.kongpf8848.dmail.mailcore.MailCore2Api
import com.kongpf8848.dmail.mailcore.SessionManager.buildImapSession
import com.libmailcore.ConnectionType
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : OAuthActivity() {
    private var imapConfig: MailConfig? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btn_gmail.setOnClickListener { v -> onClickGmail() }
        btn_outlook.setOnClickListener { v -> onClickOutlook() }
        btn_yahoo.setOnClickListener { v -> onClickYahoo() }
        btn_imap.setOnClickListener { v -> onClickImap() }
        btn_exchange.setOnClickListener { v -> onClickExchange() }
    }

    override fun onOAuthTokenSuccess(address: String?, token: OAuthToken?) {
        MailCore2Api.instance.setImapSession(
            buildImapSession(
                address,
                null,
                imapConfig!!.host,
                imapConfig!!.port,
                imapConfig!!.connection_type,
                token
            )
        )
        jumpToMessageList()
    }

    private fun onClickGmail() {
        imapConfig = MailConfig()
        imapConfig!!.host = "imap.gmail.com"
        imapConfig!!.connection_type = ConnectionType.ConnectionTypeTLS
        imapConfig!!.port = 993
        doAuth(OAuthModule.provideGoogleConfiguration(), null)
    }

    private fun onClickOutlook() {
        imapConfig = MailConfig()
        imapConfig!!.host = "imap-mail.outlook.com"
        imapConfig!!.connection_type = ConnectionType.ConnectionTypeTLS
        imapConfig!!.port = 993
        doAuth(OAuthModule.provideHotmailConfiguration(), null)
    }

    private fun onClickYahoo() {
        imapConfig = MailConfig()
        imapConfig!!.host = "imap.mail.yahoo.com"
        imapConfig!!.connection_type = ConnectionType.ConnectionTypeTLS
        imapConfig!!.port = 993
        doAuth(OAuthModule.provideYahooConfiguration(), null)
    }

    private fun onClickImap() {
        val intent = Intent(this, IMAPLoginActivity::class.java)
        startActivity(intent)
    }

    private fun onClickExchange() {
        val intent = Intent(this, IMAPLoginActivity::class.java)
        startActivity(intent)
    }

    private fun jumpToMessageList() {
        val intent = Intent(this, MessageViewListActivity::class.java)
        startActivity(intent)
    }
}