package com.kongpf8848.dmail.activity

import android.content.Intent
import android.os.Bundle
import com.kongpf8848.dmail.R
import com.kongpf8848.dmail.mailcore.MailCore2Api
import com.kongpf8848.dmail.messagelist.MessageViewListFragment
import com.libmailcore.IMAPMessage
import okhttp3.OkHttpClient
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection

class MessageViewListActivity : BaseActivity(), MessageViewListFragment.Callbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messageview_list)
    }

    override fun onItemSelected(msg: IMAPMessage) {
        MailCore2Api.instance.currentMessage = msg
        val detailIntent = Intent(this, MessageViewDetailActivity::class.java)
        startActivity(detailIntent)

        var cc=OkHttpClient();
        val request=OkHttpClient


    }
}