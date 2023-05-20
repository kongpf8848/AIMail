package com.kongpf8848.dmail.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.kongpf8848.dmail.mailcore.MailCore2Api
import com.kongpf8848.dmail.mailcore.SessionManager
import com.kongpf8848.dmail.util.DomainUtils.getDomain
import com.libmailcore.ConnectionType
import kotlinx.android.synthetic.main.activity_login_imap.*

class IMAPLoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tv_login.setOnClickListener { onClickLogin() }
    }

    private fun onClickLogin() {
        val username = et_username.text.toString().trim()
        val password = et_password.text.toString().trim()
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            return
        }
        val domain = getDomain(username)
        if (TextUtils.isEmpty(domain)) {
            return
        }
        val host = "imap.$domain"
        val connectType = ConnectionType.ConnectionTypeTLS
        val port = 993
        MailCore2Api.instance.setImapSession(
            SessionManager.buildImapSession(
                username,
                password,
                host,
                port,
                connectType,
                null
            )
        )
        val intent = Intent(this, MessageViewListActivity::class.java)
        startActivity(intent)
        finish()
    }
}