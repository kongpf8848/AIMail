package com.libmailcore.androidexample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.libmailcore.androidexample.R;

public class MainActivity extends BaseActivity{

    private Button btn_outlook;
    private Button btn_imap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_outlook=findViewById(R.id.btn_outlook);
        btn_imap=findViewById(R.id.btn_imap);
        btn_outlook.setOnClickListener(v -> {

        });
        btn_imap.setOnClickListener(v -> {
            Intent intent=new Intent(this,MessageViewListActivity.class);
            startActivity(intent);
        });

    }
}
