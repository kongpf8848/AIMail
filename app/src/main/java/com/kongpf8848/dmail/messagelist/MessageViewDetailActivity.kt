package com.kongpf8848.dmail.messagelist

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.kongpf8848.dmail.R

class MessageViewDetailActivity : com.kongpf8848.dmail.library.base.BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messageview_detail)
        if (savedInstanceState == null) {
            val fragment = MessageViewDetailFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.messageview_detail_container, fragment)
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            navigateUpTo(Intent(this, MessageViewListActivity::class.java))
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}