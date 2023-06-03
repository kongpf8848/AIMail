package com.kongpf8848.dmail.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.kongpf8848.dmail.R
import com.kongpf8848.dmail.messagelist.MessageViewListActivity
import com.kongpf8848.dmail.exchange.ExchangeManager
import com.kongpf8848.dmail.exchange.api.AccountApi
import com.kongpf8848.dmail.library.base.BaseActivity
import com.kongpf8848.dmail.mailcore.MailCore2Api
import com.kongpf8848.dmail.mailcore.SessionManager
import com.kongpf8848.dmail.util.DomainUtils
import com.libmailcore.ConnectionType
import kotlinx.android.synthetic.main.activity_account_auth.*

class MailAccountAuthActivity : BaseActivity() {

    private var accountType: DMAccountType? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_auth)
        initIntent()
        tv_login.setOnClickListener { onClickLogin() }
    }

    private fun initIntent() {
        val str = intent.getStringExtra("accountType")
        accountType = if (str != null) DMAccountType.valueOf(str) else null
    }

    private fun onClickLogin() {
        var address = et_username.text.toString().trim()
        var password = et_password.text.toString().trim()
        if (TextUtils.isEmpty(address) || TextUtils.isEmpty(password)) {
            return
        }
        val domain = DomainUtils.getDomain(address)
        if (TextUtils.isEmpty(domain)) {
            return
        }
        if (DMAccountType.TYPE_IMAP == accountType) {
            val host = "imap.$domain"
            val connectType = ConnectionType.ConnectionTypeTLS
            val port = 993
            MailCore2Api.instance.setImapSession(
                SessionManager.buildImapSession(
                    address,
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
        } else if (DMAccountType.TYPE_EXCHANGE == accountType) {
            val url = ""
            val username = DomainUtils.getUsername(address)
            val domain = DomainUtils.getDomain(address)
            ExchangeManager.addExchangeSession(username, password, domain, url)
            val session = ExchangeManager.getSession(address)
            AccountApi.checkAccount(
                session=session!!,
                successCallBack = {
                    Log.d(TAG, "successCallBack() called，inboxId:$it")
                },
                failCallBack = {
                    Log.d(TAG, "failCallBack() called，exception=${it}")
                }
            )
        }

    }
}