package com.libmailcore.androidexample;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

import android.view.MenuItem;


public class MessageViewDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messageview_detail);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {


            MessageViewDetailFragment fragment = new MessageViewDetailFragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.messageview_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, MessageViewListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
