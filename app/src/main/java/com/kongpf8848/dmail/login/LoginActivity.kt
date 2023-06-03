package com.kongpf8848.dmail.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.kongpf8848.dmail.R
import com.kongpf8848.dmail.bean.MailConfig
import com.kongpf8848.dmail.bean.OAuthToken
import com.kongpf8848.dmail.login.oauth.DaggerOAuthComponent
import com.kongpf8848.dmail.mailcore.MailCore2Api.Companion.instance
import com.kongpf8848.dmail.mailcore.SessionManager.buildImapSession
import com.kongpf8848.dmail.messagelist.MessageViewListActivity
import com.libmailcore.ConnectionType

class LoginActivity : OAuthActivity() {
    private var imapConfig: MailConfig? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        DaggerOAuthComponent.builder().build().inject(this)
        findViewById<View>(R.id.ll_gmail).setOnClickListener { v: View? -> onClickGmail() }
        findViewById<View>(R.id.ll_outlook).setOnClickListener { v: View? -> onClickOutlook() }
        findViewById<View>(R.id.ll_office365).setOnClickListener { v: View? -> onClickOffice365() }
        findViewById<View>(R.id.ll_yahoo).setOnClickListener { v: View? -> onClickYahoo() }
        findViewById<View>(R.id.ll_exchange).setOnClickListener { v: View? -> onClickExchange() }
        findViewById<View>(R.id.ll_other).setOnClickListener { v: View? -> onClickOther() }
    }

    override fun onOAuthTokenSuccess(address: String?, token: OAuthToken?) {
        instance.setImapSession(
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
        doAuth(DMAccountType.TYPE_GOOGLE, null)
    }

    private fun onClickOutlook() {
        imapConfig = MailConfig()
        imapConfig!!.host = "imap-mail.outlook.com"
        imapConfig!!.connection_type = ConnectionType.ConnectionTypeTLS
        imapConfig!!.port = 993
        doAuth(DMAccountType.TYPE_HOTMAIL, null)
    }

    private fun onClickOffice365() {
        imapConfig = MailConfig()
        imapConfig!!.host = "outlook.office365.com"
        imapConfig!!.connection_type = ConnectionType.ConnectionTypeTLS
        imapConfig!!.port = 993
        doAuth(DMAccountType.TYPE_OFFICE365, null)
    }

    private fun onClickYahoo() {
        imapConfig = MailConfig()
        imapConfig!!.host = "imap.mail.yahoo.com"
        imapConfig!!.connection_type = ConnectionType.ConnectionTypeTLS
        imapConfig!!.port = 993
        doAuth(DMAccountType.TYPE_YAHOO, null)
    }

    private fun onClickOther() {
        val intent = Intent(this, MailAccountAuthActivity::class.java)
        intent.putExtra("accountType", DMAccountType.TYPE_IMAP.toString())
        startActivity(intent)
    }

    private fun onClickExchange() {
        val intent = Intent(this, MailAccountAuthActivity::class.java)
        intent.putExtra("accountType", DMAccountType.TYPE_EXCHANGE.toString())
        startActivity(intent)
    }

    private fun jumpToMessageList() {
        val intent = Intent(this, MessageViewListActivity::class.java)
        startActivity(intent)
    }
}