package com.libmailcore.androidexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.libmailcore.IMAPMessage;


public class MessageViewListActivity extends Activity implements MessageViewListFragment.Callbacks {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messageview_list);
    }


    @Override
    public void onItemSelected(IMAPMessage msg) {
        MessagesSyncManager.singleton().currentMessage = msg;
        Intent detailIntent = new Intent(this, MessageViewDetailActivity.class);
        startActivity(detailIntent);

    }
}
