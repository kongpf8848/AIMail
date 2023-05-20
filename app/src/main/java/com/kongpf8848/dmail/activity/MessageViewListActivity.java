package com.kongpf8848.dmail.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.kongpf8848.dmail.R;
import com.libmailcore.IMAPMessage;
import com.kongpf8848.dmail.messagelist.MessageViewListFragment;
import com.kongpf8848.dmail.mailcore.MailCore2Api;


public class MessageViewListActivity extends Activity implements MessageViewListFragment.Callbacks {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messageview_list);
    }


    @Override
    public void onItemSelected(IMAPMessage msg) {
        MailCore2Api.getInstance().currentMessage = msg;
        Intent detailIntent = new Intent(this, MessageViewDetailActivity.class);
        startActivity(detailIntent);

    }
}
