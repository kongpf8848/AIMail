package com.kongpf8848.dmail.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.kongpf8848.dmail.MessageViewDetailFragment;
import com.kongpf8848.dmail.R;


public class MessageViewDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messageview_detail);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
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
